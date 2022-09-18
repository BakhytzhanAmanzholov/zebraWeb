package kz.jaguars.hackathon.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Hashtable;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QRController {

    @GetMapping(
            value = "/get-image-dynamic-type"
    )
    public ResponseEntity<?> getImageDynamicType() {
        MediaType contentType = MediaType.IMAGE_PNG;
        String qrCodeText = "https://www.journaldev.com";
        String filePath = "JD.png";
        int size = 125;
        String fileType = "png";
        File qrFile = new File(filePath);
        try {
            createQRImage(qrFile, qrCodeText, size, fileType);
        }
        catch (IOException | WriterException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        InputStream in = getClass().getResourceAsStream("/kz/jaguars/hackathon/JD.png");
        assert in != null;
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }

    private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
            throws WriterException, IOException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, fileType, qrFile);
    }
}
