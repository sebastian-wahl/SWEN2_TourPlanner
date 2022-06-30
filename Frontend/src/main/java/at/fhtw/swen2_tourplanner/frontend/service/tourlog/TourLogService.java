package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourLogService extends Service {
    List<TourLog> getAllLogs(UUID tourId) throws BackendConnectionException, ApiCallTimoutException;

    boolean deleteTourLog(TourLog tourLog) throws BackendConnectionException, ApiCallTimoutException;

    Optional<TourLog> addTourLog(TourLog tourLog) throws BackendConnectionException, ApiCallTimoutException;

    Optional<TourLog> updateTourLog(TourLog tourLog) throws BackendConnectionException, ApiCallTimoutException;

    byte[] getTourReport(UUID tourId) throws BackendConnectionException, ApiCallTimoutException;

    byte[] getTourSummary() throws BackendConnectionException, ApiCallTimoutException;

    Optional<Tour> getComputedTourAttributes(UUID tourId) throws BackendConnectionException, ApiCallTimoutException;
}
