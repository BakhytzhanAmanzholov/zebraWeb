package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CoffeeWithSalesDto {
    private Long id;
    private String shorName;
    private String address;
    private String workingHours;

    private Integer countSales;
    private Double averageBill;
    private Integer profit;
    private Integer expenses;
    private Integer marginality;
    private Double salesVolume;

    private ProductDto bestProduct;


    private Set<StaffDto> staffs;
}
