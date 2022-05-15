package at.fhtw.swen2_tourplanner.backend.tourlog.repo;

import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
}
