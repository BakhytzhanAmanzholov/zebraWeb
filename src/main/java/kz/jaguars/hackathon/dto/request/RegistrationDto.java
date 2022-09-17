package kz.jaguars.hackathon.dto.request;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String username;
    private String password;
}
