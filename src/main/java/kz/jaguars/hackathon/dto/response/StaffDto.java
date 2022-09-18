package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String role;

}
