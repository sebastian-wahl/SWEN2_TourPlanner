package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;

import java.util.List;
import java.util.UUID;

public interface TourService {
    Tour createTour(Tour tour) throws BusinessException;

    Tour updateTour(Tour tour) throws BusinessException;

    //Service Method declarations
    Tour getTour(UUID id) throws BusinessException;

    boolean deleteTour(UUID id);

    List<Tour> getAllTours();
}
