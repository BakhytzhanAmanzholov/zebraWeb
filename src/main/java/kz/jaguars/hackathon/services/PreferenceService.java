package kz.jaguars.hackathon.services;

import kz.jaguars.hackathon.models.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceService extends JpaRepository<Preference, Long> {
}
