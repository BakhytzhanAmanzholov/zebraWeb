package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.models.Staff;
import kz.jaguars.hackathon.repositories.StaffRepository;
import kz.jaguars.hackathon.services.CoffeeService;
import kz.jaguars.hackathon.services.StaffService;
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
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    private final PasswordEncoder passwordEncoder;

    private final CoffeeService coffeeService;

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

        account.setCoffeeHouse(coffeeService.findById(coffeeHouse));

        return save(account);
    }

    @Override
    public Staff save(Staff user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmed(true);
        user.setState(CONFIRMED);
//        emailSenderService.sendEmail(user.getEmail(), user.getUsername()); // Отправка письма
        user = staffRepository.save(user);
        coffeeService.addStaffToCoffeeHouse(user, user.getCoffeeHouse().getId());
        return user;
    }

    @Override
    public void delete(Long id) {
        Staff account = findById(id);
        staffRepository.delete(account);
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
        return staffRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User <" + id + "> not found"));

    }

    @Override
    public Staff findByEmail(String email) {
        return staffRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
