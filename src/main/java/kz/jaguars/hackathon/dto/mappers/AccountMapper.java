package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.dto.response.ClientDto;
import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.models.Product;
import kz.jaguars.hackathon.models.Staff;

import java.util.HashSet;

public class AccountMapper {
    public static Staff fromRequestDto(RegistrationDto registrationDto){
        return Staff.builder()
                .email(registrationDto.getEmail())
                .name(registrationDto.getName())
                .surname(registrationDto.getSurname())
                .password(registrationDto.getPassword())
                .banned(true)
                .confirmed(false)
                .build();
    }

    public static ClientDto toResponseDto(Account account){
        ClientDto dto =  ClientDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .username(account.getUsername())
                .preferences(new HashSet<>())
                .build();
        for (Product product: account.getPreferences()){
            dto.getPreferences().add(ProductMapper.toResponseDto(product));
        }
        return dto;
    }
}
