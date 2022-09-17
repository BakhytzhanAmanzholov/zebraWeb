package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
