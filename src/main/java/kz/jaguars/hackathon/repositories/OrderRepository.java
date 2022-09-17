package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.Booking;
import kz.jaguars.hackathon.models.CoffeeHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByCoffeeHouse(CoffeeHouse coffeeHouse);
}
