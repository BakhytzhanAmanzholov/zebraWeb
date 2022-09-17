package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private Date date;
    private StaffDto staff;
    private ClientDto client;
    private List<ProductDto> products;
}
