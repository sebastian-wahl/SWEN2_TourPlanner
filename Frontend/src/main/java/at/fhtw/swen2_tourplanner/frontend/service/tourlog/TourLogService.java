package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourLogService extends Service {
    List<TourLog> getAllLogs(UUID tourId) throws BackendConnectionException;

    boolean deleteTourLog(TourLog tourLog) throws BackendConnectionException;

    Optional<TourLog> addTourLog(TourLog tourLog) throws BackendConnectionException;

    Optional<TourLog> updateTourLog(TourLog tourLog) throws BackendConnectionException;

    byte[] getTourReport(UUID tourId) throws BackendConnectionException;

    byte[] getTourSummary() throws BackendConnectionException;

    Optional<Tour> getComputedTourAttributes(UUID tourId) throws BackendConnectionException;
}
