package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourService extends Service {
    List<Tour> getAllTours() throws BackendConnectionException;

    Optional<Tour> getTourById(UUID id) throws BackendConnectionException;

    boolean deleteTour(Tour tour) throws BackendConnectionException;

    Optional<Tour> addTour(Tour tour) throws BackendConnectionException;

    List<Tour> importTours(List<Tour> tourList) throws BackendConnectionException;

    Optional<Tour> updateTour(Tour tour) throws BackendConnectionException;


}