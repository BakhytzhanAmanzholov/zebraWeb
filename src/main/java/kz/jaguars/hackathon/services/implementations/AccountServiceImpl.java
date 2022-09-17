package kz.jaguars.hackathon.services.implementations;

import kz.jaguars.hackathon.exceptions.NotFoundException;
import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.repositories.AccountRepository;
import kz.jaguars.hackathon.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Client <" + id + "> not found"));
    }

    @Override
    public void addOrderToAccount(Booking booking, Long id) {
        Account account = findById(id);
        account.getOrders().add(booking);
    }
}
