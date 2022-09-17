package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.response.OrderDto;
import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.models.Product;

import java.util.ArrayList;

public class OrderMapper {
    public static OrderDto toResponseDto(Booking order){
        OrderDto dto = OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .staff(StaffMapper.toResponseDto(order.getStaff()))
                .client(AccountMapper.toResponseDto(order.getAccount()))
                .products(new ArrayList<>())
                .build();

        for (Product product: order.getProducts()){
            dto.getProducts().add(ProductMapper.toResponseDto(product));
        }
        return dto;
    }
}
