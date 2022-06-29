package at.fhtw.swen2_tourplanner.backend.tourlog.rest;

import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.service.TourLogService;
import at.fhtw.swen2_tourplanner.backend.tourlog.util.ComputedValues;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "tour-log")
public class TourLogController {
    private final TourLogService tourLogService;

    @Autowired
    public TourLogController(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createTourLog(@RequestBody TourLogDTO tourLogDto) {
        try {
            TourLogDTO tourLog = tourLogService.createTourLog(tourLogDto);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTourLog(@RequestBody TourLogDTO tourLogDto) {
        try {
            TourLogDTO tourLog = tourLogService.updateTourLog(tourLogDto);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> getTourLogsForTour(@PathVariable("id") UUID tourId) {
        try {
            List<TourLogDTO> tourLogs = tourLogService.getAllByTourId(tourId);
            return new ResponseEntity<>(tourLogs, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTourLog(@PathVariable("id") UUID id) {
        if (tourLogService.deleteTourLog(id)) {
            return new ResponseEntity<>("Tour Log deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tour Log not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get-summary-report")
    public ResponseEntity<Object> getSummaryReport() {
        try {
            final byte[] pdfFile = tourLogService.getSummaryReport();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get-tour-report/{id}")
    public ResponseEntity<Object> getTourReport(@PathVariable("id") UUID id) {
        try {
            final byte[] pdfFile = tourLogService.getTourReport(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get-computed-values/{id}")
    public ResponseEntity<Object> getComputedValues(@PathVariable("id") UUID tourId) {
        try {
            List<TourLogDTO> tourLogs = tourLogService.getAllByTourId(tourId);
            ComputedValues computedValues = new ComputedValues(tourLogs);
            return new ResponseEntity<>(computedValues, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>("Values could not be generated properly", HttpStatus.NOT_FOUND);
        }
    }
}
