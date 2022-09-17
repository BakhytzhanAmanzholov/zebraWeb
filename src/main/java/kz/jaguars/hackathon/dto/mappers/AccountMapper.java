package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.models.Account;

import java.util.ArrayList;

public class AccountMapper {
    public static Account fromRequestDto(RegistrationDto registrationDto){
        return Account.builder()
                .email(registrationDto.getEmail())
                .username(registrationDto.getUsername())
                .password(registrationDto.getPassword())
                .banned(true)
                .confirmed(false)
                .build();
    }
}
