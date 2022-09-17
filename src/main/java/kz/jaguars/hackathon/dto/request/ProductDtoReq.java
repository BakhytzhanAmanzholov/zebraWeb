package kz.jaguars.hackathon.dto.request;

import lombok.Data;

@Data
public class ProductDtoReq {
    private String title;
    private String description;
    private Integer costPrice;
    private Integer salePrice;
    private String category;
}
