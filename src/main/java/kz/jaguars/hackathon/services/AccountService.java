package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Account;

public interface AccountService {

    void delete(Long id);
    Account findById(Long id);

}
