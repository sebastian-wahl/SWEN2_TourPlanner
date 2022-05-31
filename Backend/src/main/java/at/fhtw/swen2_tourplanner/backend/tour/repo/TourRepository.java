package at.fhtw.swen2_tourplanner.backend.tour.repo;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//Access to database
public interface TourRepository extends JpaRepository<Tour, UUID> {
}
