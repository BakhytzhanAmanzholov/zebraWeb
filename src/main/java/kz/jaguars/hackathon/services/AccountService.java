package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Account;

public interface AccountService extends CrudService<Account, Long>{
    Account findByEmail(String email);
}
