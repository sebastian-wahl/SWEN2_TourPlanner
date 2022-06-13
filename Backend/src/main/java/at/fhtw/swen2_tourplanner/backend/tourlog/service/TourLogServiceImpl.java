package at.fhtw.swen2_tourplanner.backend.tourlog.service;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import at.fhtw.swen2_tourplanner.backend.tourlog.repo.TourLogRepository;
import at.fhtw.swen2_tourplanner.backend.tourlog.util.TourLogPdfHelper;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TourLogServiceImpl implements TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourService tourService;
    private final TourLogPdfHelper tourLogPdfHelper;

    private final String SUMMARY_REPORT_NAME = "summary_report";

    @Autowired
    public TourLogServiceImpl(TourLogRepository tourLogRepository, TourService tourService, TourLogPdfHelper tourLogPdfHelper) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
        this.tourLogPdfHelper = tourLogPdfHelper;
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
        TourDTO tourDTO;
        try {
            tourDTO = tourService.getTour(tourLogDto.getTour());
        } catch (Exception e) {
            throw e;
        }
        TourLog tourLog = new TourLog(tourLogDto, tourDTO);
        tourLog.setId(tourLogDto.getId());
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

    @Override
    public byte[] getTourReport(UUID id) {
        try {
            TourDTO tour = tourService.getTour(id);
            List<TourLogDTO> tourLogs = tourLogRepository.findByTourId(id).stream().map(TourLogDTO::new).collect(Collectors.toList());
            tourLogPdfHelper.createTourReport(tour, tourLogs);
            return tourLogPdfHelper.getPdfFile(String.valueOf(tour.getId()));
        } catch (Exception e) {
            throw new BusinessException("Could not create report");
        }
    }

    @Override
    public byte[] getSummaryReport() {
        try {
            List<TourDTO> tours = tourService.getAllTours();
            HashMap<TourDTO, List<TourLogDTO>> allToursAndLogs = new HashMap<>();
            for (TourDTO tour : tours) {
                List<TourLogDTO> tourLogs = tourLogRepository.findByTourId(tour.getId()).stream().map(TourLogDTO::new).collect(Collectors.toList());
                allToursAndLogs.put(tour, tourLogs);
            }
            tourLogPdfHelper.createSummaryReport(allToursAndLogs);
            return tourLogPdfHelper.getPdfFile(SUMMARY_REPORT_NAME);
        } catch (Exception e) {
            throw new BusinessException("Could not create report");
        }
    }
}
