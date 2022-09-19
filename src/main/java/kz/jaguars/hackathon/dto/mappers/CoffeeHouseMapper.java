package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.response.CoffeeHouseDto;
import kz.jaguars.hackathon.dto.response.CoffeeWithSalesDto;
import kz.jaguars.hackathon.models.BestProduct;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.models.Staff;

import java.util.HashSet;

public class CoffeeHouseMapper {
    public static CoffeeHouseDto toResponseDto(CoffeeHouse coffeeHouse) {
        CoffeeHouseDto dto = CoffeeHouseDto.builder()
                .id(coffeeHouse.getId())
                .address(coffeeHouse.getAddress())
                .shorName(coffeeHouse.getShortName())
                .workingHours(coffeeHouse.getWorkingHours())
                .staffs(new HashSet<>())
                .build();

        for (Staff staff : coffeeHouse.getStaffs()) {
            dto.getStaffs().add(StaffMapper.toResponseDto(staff));
        }

        return dto;
    }

    public static CoffeeWithSalesDto toResponseSalesDto(CoffeeHouse coffeeHouse) {
        CoffeeWithSalesDto dto = CoffeeWithSalesDto.builder()
                .id(coffeeHouse.getId())
                .address(coffeeHouse.getAddress())
                .shorName(coffeeHouse.getShortName())
                .workingHours(coffeeHouse.getWorkingHours())
                .marginality(coffeeHouse.getMarginality())
                .salesVolume(coffeeHouse.getSalesVolume())
                .averageBill(coffeeHouse.getAverageBill())
                .countSales(coffeeHouse.getCountSales())
                .expenses(coffeeHouse.getExpenses())
                .profit(coffeeHouse.getProfit())
                .staffs(new HashSet<>())
                .build();

        int max = 0;

        for (BestProduct bestProduct : coffeeHouse.getBestProducts()) {
            if (bestProduct.getCount() > max) {
                dto.setBestProduct(ProductMapper.toResponseDto(bestProduct.getProduct()));
            }
        }

        for (Staff staff : coffeeHouse.getStaffs()) {
            dto.getStaffs().add(StaffMapper.toResponseDto(staff));
        }

        return dto;
    }
}
