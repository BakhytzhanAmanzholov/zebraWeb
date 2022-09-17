package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderHistoryDto {
    private Long id;
    private String date;
    private Boolean completed;
    private StaffDto staff;
    private List<ProductDto> products;
    private Integer price;

}
