package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.models.Booking;

public interface AccountService {

    void delete(Long id);
    Account findById(Long id);

    void addOrderToAccount(Booking booking, Long id);
}
