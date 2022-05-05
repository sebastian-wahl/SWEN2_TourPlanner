package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;

import java.util.List;

public interface TourService {
    void createTour(Tour tour);

    void updateTour(Tour tour) throws Exception;

    //Service Method declarations
    Tour getTour(long id) throws Exception;
    void deleteTour(long id);
    List<Tour> getAllTours();
}
