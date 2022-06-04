package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourLogService extends Service {
    List<TourLog> getAllLogs(UUID tourId);

    boolean deleteTourLog(TourLog tourLog);

    Optional<TourLog> addTourLog(TourLog tourLog);

    Optional<TourLog> updateTourLog(TourLog tourLog);
}
