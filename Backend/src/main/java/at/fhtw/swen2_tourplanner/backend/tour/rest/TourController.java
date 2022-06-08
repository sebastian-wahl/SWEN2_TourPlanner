package at.fhtw.swen2_tourplanner.backend.tour.rest;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

//Rest Endpoint
@RestController
@RequestMapping(value = "tour")
public class TourController {
    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createTour(@RequestBody TourDTO tour) {
        try {
            TourDTO addedTour = tourService.createTour(tour);
            return new ResponseEntity<>(addedTour, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTour(@RequestBody TourDTO tour) {
        try {
            TourDTO updatedTour = tourService.updateTour(tour);
            return new ResponseEntity<>(updatedTour, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAllTours() {
        List<TourDTO> tours = tourService.getAllTours();
        if (!tours.isEmpty()) {
            return new ResponseEntity<>(tours, HttpStatus.OK);
        }
        return new ResponseEntity<>("Tours could not be found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> getTour(@PathVariable("id") UUID id) {
        try {
            TourDTO tour = tourService.getTour(id);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTour(@PathVariable("id") UUID id) throws IOException {
        if (tourService.deleteTour(id)) {
            return new ResponseEntity<>("Tour deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tour not deleted", HttpStatus.NOT_FOUND);
    }
}
