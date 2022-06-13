package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourService extends Service {
    List<Tour> getAllTours() throws BackendConnectionException, ApiCallTimoutException;

    Optional<Tour> getTourById(UUID id) throws BackendConnectionException, ApiCallTimoutException;

    boolean deleteTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException;

    Optional<Tour> addTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException;

    Optional<Tour> updateTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException;
}