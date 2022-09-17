package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.request.ProductDtoReq;
import kz.jaguars.hackathon.dto.response.ProductDto;
import kz.jaguars.hackathon.models.Product;

public class ProductMapper {
    public static ProductDto toResponseDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .salePrice(product.getSalePrice())
                .category(product.getCategory().getTitle())
                .build();
    }

    public static Product fromRequestDto(ProductDtoReq dto){
        return Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .costPrice(dto.getCostPrice())
                .salePrice(dto.getSalePrice())
                .build();
    }
}
