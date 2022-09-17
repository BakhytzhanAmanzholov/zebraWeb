package kz.jaguars.hackathon.dto.mappers;

import kz.jaguars.hackathon.dto.response.StaffDto;
import kz.jaguars.hackathon.models.Staff;

public class StaffMapper {
    public static StaffDto toResponseDto(Staff staff){
        return StaffDto.builder()
                .id(staff.getId())
                .email(staff.getEmail())
                .name(staff.getName())
                .surname(staff.getSurname())
                .role(staff.getRole().name())
                .build();
    }
}
