package at.fhtw.swen2_tourplanner.backend.tour.repo;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

//Access to database
public interface TourRepository extends JpaRepository<Tour, Long> {
    /*
    Custom Method Example:
        @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
        Foo retrieveByName(@Param("name") String name);
    */
}
