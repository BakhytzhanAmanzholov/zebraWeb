package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
