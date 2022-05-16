package at.fhtw.swen2_tourplanner.backend.tourlog.repo;

import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
    @Query(value = "SELECT t FROM TourLog t WHERE t.tour.id = :tourId")
    List<TourLog> findByTourId(@Param("tourId") UUID tourId);
}
