package at.fhtw.swen2_tourplanner.backend.tourlog.service;

import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;

import java.util.UUID;

public interface TourLogService {
    TourLogDTO createTourLog(TourLogDTO tourLogDto);

    TourLogDTO updateTourLog(TourLogDTO tourLogDto);

    boolean deleteTourLog(UUID id);
}
