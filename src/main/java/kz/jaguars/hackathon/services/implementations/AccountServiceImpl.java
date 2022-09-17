package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.repositories.AccountRepository;
import kz.jaguars.hackathon.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kz.jaguars.hackathon.models.Account.State.NOT_CONFIRMED;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Account save(Account user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmed(false);
        user.setState(NOT_CONFIRMED);
//        emailSenderService.sendEmail(user.getEmail(), user.getUsername()); // Отправка письма
        user = accountRepository.save(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        Account account = findById(id);
        accountRepository.delete(account);
    }

    @Override
    public Account update(Account entity) {
        Account account = findById(entity.getId());
        account.setUsername(entity.getUsername());
        return account;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User <" + id + "> not found"));

    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
