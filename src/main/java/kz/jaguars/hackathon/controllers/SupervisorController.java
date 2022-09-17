package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.AccountMapper;
import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/supervisor")
public class SupervisorController {
    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto) {
        try {
            accountService.findByEmail(dto.getEmail());
        } catch (UsernameNotFoundException e) {
            Staff account = AccountMapper.fromRequestDto(dto);
            account = accountService.saveWithRole(account, dto.getCoffeeHouse(), dto.getRole());
            return new ResponseEntity<>("The account " + account.getEmail() + " successfully registered by role "
                    + account.getRole(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }

}
