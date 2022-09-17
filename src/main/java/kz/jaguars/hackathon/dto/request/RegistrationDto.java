package kz.jaguars.hackathon.dto.request;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String name;
    private String surname;
    private String password;
    private Long coffeeHouse;
    private String role;
}
