package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.models.Staff;

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
}
