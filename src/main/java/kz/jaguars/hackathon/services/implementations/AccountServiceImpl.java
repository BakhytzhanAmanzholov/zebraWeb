package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.repositories.AccountRepository;
import kz.jaguars.hackathon.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kz.jaguars.hackathon.models.Staff.State.CONFIRMED;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Staff saveWithRole(Staff account, Long coffeeHouse, String roleName) {
        if(Staff.Role.ADMIN.toString().equals(roleName)){
            account.setRole(Staff.Role.ADMIN);
        }
        else if(Staff.Role.CASHIER.toString().equals(roleName)){
            account.setRole(Staff.Role.CASHIER);
        }
        else if(Staff.Role.SUPERVISOR.toString().equals(roleName)){
            account.setRole(Staff.Role.SUPERVISOR);
        }
        else if(Staff.Role.USER.toString().equals(roleName)){
            account.setRole(Staff.Role.USER);
        }

// coffee house

        return save(account);
    }

    @Override
    public Staff save(Staff user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmed(true);
        user.setState(CONFIRMED);
//        emailSenderService.sendEmail(user.getEmail(), user.getUsername()); // Отправка письма
        user = accountRepository.save(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        Staff account = findById(id);
        accountRepository.delete(account);
    }

    @Override
    public Staff update(Staff entity) {
        Staff account = findById(entity.getId());
        account.setName(entity.getName());
        account.setSurname(entity.getSurname());
        return account;
    }

    @Override
    public Staff findById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User <" + id + "> not found"));

    }

    @Override
    public Staff findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
