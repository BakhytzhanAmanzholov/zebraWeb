package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class CoffeeHouseDto {
    private Long id;
    private String shorName;
    private String address;
    private String workingHours;
    private Set<StaffDto> staffs;
}
