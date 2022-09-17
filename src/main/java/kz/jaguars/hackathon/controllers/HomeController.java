package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.dto.mappers.AccountMapper;
import kz.jaguars.hackathon.dto.request.RegistrationDto;
import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto dto) {
        try {
            accountService.findByEmail(dto.getEmail());
        } catch (UsernameNotFoundException e) {
            Account account = AccountMapper.fromRequestDto(dto);
            accountService.save(account);
            return new ResponseEntity<>("Please confirm your email", HttpStatus.OK);
        }
        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }
}
