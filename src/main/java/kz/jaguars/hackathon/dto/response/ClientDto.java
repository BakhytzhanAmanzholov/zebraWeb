package kz.jaguars.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ClientDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Set<ProductDto> preferences;
    private List<OrderHistoryDto> orders;
}
