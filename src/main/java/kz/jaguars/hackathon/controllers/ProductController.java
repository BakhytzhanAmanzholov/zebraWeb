package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.ProductMapper;
import kz.jaguars.hackathon.dto.request.ProductDtoReq;
import kz.jaguars.hackathon.dto.response.ProductDto;
import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Product> products = productService.findAll();
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : products) {
            dtoList.add(ProductMapper.toResponseDto(product));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDtoReq dto){
        try {
            productService.findByTitle(dto.getTitle());
        }
        catch (NotFoundException e){
            Product product = ProductMapper.fromRequestDto(dto);
            product = productService.saveWithCategory(product, dto.getCategory());
            return new ResponseEntity<>("The product " + product.getTitle() + " successfully added by sale price "
                    + product.getSalePrice(), HttpStatus.OK);
        }
        return new ResponseEntity<>("This title is already taken!", HttpStatus.BAD_REQUEST);
    }
}
