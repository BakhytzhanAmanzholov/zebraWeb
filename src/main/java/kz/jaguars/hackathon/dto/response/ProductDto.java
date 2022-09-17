package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private Integer costPrice;
    private Integer salePrice;
    private String category;
}
