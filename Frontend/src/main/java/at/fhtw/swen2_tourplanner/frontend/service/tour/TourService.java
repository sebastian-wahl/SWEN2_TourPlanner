package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourService extends Service {
    List<Tour> getAllTours();

    Optional<Tour> getTourById(UUID id);

    boolean deleteTour(Tour tour);

    Optional<Tour> addTour(Tour tour);

    Optional<Tour> updateTour(Tour tour);
}