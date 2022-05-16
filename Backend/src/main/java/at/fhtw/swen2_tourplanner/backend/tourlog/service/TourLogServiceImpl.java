package at.fhtw.swen2_tourplanner.backend.tourlog.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import at.fhtw.swen2_tourplanner.backend.tourlog.repo.TourLogRepository;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TourLogServiceImpl implements TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourService tourService;

    @Autowired
    public TourLogServiceImpl(TourLogRepository tourLogRepository, TourService tourService) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
    }

    @Override
    public TourLogDTO createTourLog(TourLogDTO tourLogDto) {
        if (tourLogDto.getId() == null) {
            return saveTourLog(tourLogDto);
        } else {
            throw new BusinessException("Tour Log already exists");
        }
    }

    @Override
    public TourLogDTO updateTourLog(TourLogDTO tourLogDto) {
        if (tourLogDto.getId() == null) {
            throw new BusinessException("No Id supplied");
        } else if (tourLogRepository.findById(tourLogDto.getId()).isPresent()) {
            return saveTourLog(tourLogDto);
        } else {
            throw new BusinessException("Tour Log not found");
        }
    }

    private TourLogDTO saveTourLog(TourLogDTO tourLogDto) {
        Tour tour;
        try {
            tour = tourService.getTour(tourLogDto.getTour());
        } catch (Exception e) {
            throw e;
        }
        TourLog tourLog = new TourLog(tourLogDto, tour);
        tourLogRepository.save(tourLog);
        return new TourLogDTO(tourLog);
    }

    @Override
    public boolean deleteTourLog(UUID id) {
        try {
            tourLogRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public List<TourLogDTO> getAllByTourId(UUID id) {
        List<TourLog> tourLogs = tourLogRepository.findByTourId(id);
        return tourLogs.stream().map(TourLogDTO::new).collect(Collectors.toList());
    }
}
