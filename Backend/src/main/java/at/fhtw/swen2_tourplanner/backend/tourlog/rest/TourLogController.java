package at.fhtw.swen2_tourplanner.backend.tourlog.rest;

import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import at.fhtw.swen2_tourplanner.backend.tourlog.service.TourLogService;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> createTour(@RequestBody TourLogDTO tourLogDto) {
        try {
            TourLog tourLog = tourLogService.createTourLog(tourLogDto);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTour(@RequestBody TourLogDTO tourLogDto) {
        try {
            TourLog tourLog = tourLogService.updateTourLog(tourLogDto);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTour(@PathVariable("id") UUID id) {
        if (tourLogService.deleteTourLog(id)) {
            return new ResponseEntity<>("Tour Log deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tour Log not deleted", HttpStatus.NOT_FOUND);
    }
}
