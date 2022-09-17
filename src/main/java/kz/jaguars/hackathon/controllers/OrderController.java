package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.OrderMapper;
import kz.jaguars.hackathon.dto.response.OrderDto;
import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}/cafe")
    public ResponseEntity<?> findAllByCoffee(@PathVariable("id") Long id){
        List<Booking> orders = orderService.findAllByCoffee(id);
        List<OrderDto> dtoList = new ArrayList<>();

        for (Booking order: orders){
            dtoList.add(OrderMapper.toResponseDto(order));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> save(@PathVariable("id") Long id){
        Booking booking = orderService.saveWithCoffee(id);
        return new ResponseEntity<>("Save order by id " + booking.getId(), HttpStatus.OK);
    }
}
