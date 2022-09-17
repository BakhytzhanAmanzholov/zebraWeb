package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
}
