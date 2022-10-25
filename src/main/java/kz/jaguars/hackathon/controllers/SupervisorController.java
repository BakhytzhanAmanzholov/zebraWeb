package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.AccountMapper;
import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.services.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/supervisor")
@CrossOrigin(maxAge = 3600, allowCredentials = "true")
public class SupervisorController {
    private final StaffService staffService;

    @PostMapping("/registration")
    @CrossOrigin(origins = "https://twitter-front-pi.vercel.app", allowedHeaders = {"Requestor-Type", "Authorization"}, exposedHeaders = "X-Get-Header")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        try {
            staffService.findByEmail(dto.getEmail());
        } catch (UsernameNotFoundException e) {
            Staff account = AccountMapper.fromRequestDto(dto);
            account = staffService.saveWithRole(account, dto.getCoffeeHouse(), dto.getRole());
            return new ResponseEntity<>("The account " + account.getEmail() + " successfully registered by role "
                    + account.getRole(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }

}
