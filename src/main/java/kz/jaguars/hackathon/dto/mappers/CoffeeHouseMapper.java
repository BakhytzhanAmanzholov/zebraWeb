package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.response.CoffeeHouseDto;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Staff;

import java.util.HashSet;

public class CoffeeHouseMapper {
    public static CoffeeHouseDto toResponseDto(CoffeeHouse coffeeHouse){
        CoffeeHouseDto dto = CoffeeHouseDto.builder()
                .id(coffeeHouse.getId())
                .address(coffeeHouse.getAddress())
                .shorName(coffeeHouse.getShortName())
                .workingHours(coffeeHouse.getWorkingHours())
                .staffs(new HashSet<>())
                .build();

        for(Staff staff: coffeeHouse.getStaffs()){
            dto.getStaffs().add(StaffMapper.toResponseDto(staff));
        }

        return dto;
    }
}
