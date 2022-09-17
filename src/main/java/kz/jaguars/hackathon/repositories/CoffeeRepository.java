package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.CoffeeHouse;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<CoffeeHouse, Long> {
}
