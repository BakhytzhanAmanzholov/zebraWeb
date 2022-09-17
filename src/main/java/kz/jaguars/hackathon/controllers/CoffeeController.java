package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.CoffeeHouseMapper;
import kz.jaguars.hackathon.dto.response.CoffeeHouseDto;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.services.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CoffeeController {
    private final CoffeeService coffeeService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<CoffeeHouse> coffeeHouses = coffeeService.findAll();
        List<CoffeeHouseDto> dtoList = new ArrayList<>();
        for(CoffeeHouse coffeeHouse: coffeeHouses){
            dtoList.add(CoffeeHouseMapper.toResponseDto(coffeeHouse));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
