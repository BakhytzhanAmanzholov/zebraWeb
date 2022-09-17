package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.response.OrderDto;
import kz.jaguars.hackathon.dto.response.OrderHistoryDto;
import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.models.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class OrderMapper {
    public static OrderDto toResponseDto(Booking order){
        OrderDto dto = OrderDto.builder()
                .id(order.getId())
                .date(order.getDate().toString())
                .staff(StaffMapper.toResponseDto(order.getStaff()))
                .products(new ArrayList<>())
                .price(order.getPrice())
                .completed(order.getCompleted())
                .finalPrice(order.getFinalPrice())
                .build();
        if(order.getAccount() != null){
            dto.setClient(AccountMapper.toResponseDto(order.getAccount()));
        }

        for (Product product: order.getProducts()){
            dto.getProducts().add(ProductMapper.toResponseDto(product));
        }
        return dto;
    }

    public static OrderHistoryDto toHistoryResponseDto(Booking order){
        OrderHistoryDto dto = OrderHistoryDto.builder()
                .id(order.getId())
                .completed(order.getCompleted())
                .date(order.getDate().toString())
                .price(order.getFinalPrice())
                .staff(StaffMapper.toResponseDto(order.getStaff()))
                .products(new ArrayList<>())
                .build();
        for (Product product: order.getProducts()){
            dto.getProducts().add(ProductMapper.toResponseDto(product));
        }
        return dto;
    }
}
