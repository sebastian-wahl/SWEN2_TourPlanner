package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;

import java.util.List;
import java.util.UUID;

public interface TourService {
    void createTour(Tour tour) throws BusinessException;

    void updateTour(Tour tour) throws BusinessException;

    //Service Method declarations
    Tour getTour(UUID id) throws BusinessException;
    void deleteTour(UUID id);
    List<Tour> getAllTours();
}
