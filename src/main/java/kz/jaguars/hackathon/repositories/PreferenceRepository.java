package kz.jaguars.hackathon.repositories;

import kz.jaguars.hackathon.models.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
