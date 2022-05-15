package at.fhtw.swen2_tourplanner.backend.tourlog.service;

import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;

import java.util.UUID;

public interface TourLogService {
    TourLog createTourLog(TourLogDTO tourLogDto);

    TourLog updateTourLog(TourLogDTO tourLogDto);

    boolean deleteTourLog(UUID id);
}
