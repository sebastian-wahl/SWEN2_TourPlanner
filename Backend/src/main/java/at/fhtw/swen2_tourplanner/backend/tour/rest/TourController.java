package at.fhtw.swen2_tourplanner.backend.tour.rest;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
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

import java.util.List;

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
    public ResponseEntity<Object> createTour(@RequestBody Tour tour) {
        tourService.createTour(tour);
        return new ResponseEntity<>("Tour created successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTour(@RequestBody Tour tour) {
        try {
            tourService.updateTour(tour);
        } catch (Exception e) {
            return new ResponseEntity<>("Tour could not be found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Tour updated successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        if (tours.size() > 0) {
            return new ResponseEntity<>(tours, HttpStatus.OK);
        }
        return new ResponseEntity<>("Tours could not be found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> getTour(@PathVariable("id") long id) {
        try {
            Tour tour = tourService.getTour(id);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Tour could not be found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTour(@PathVariable("id") long id) {
        tourService.deleteTour(id);
        return new ResponseEntity<>("Tour deleted successfully", HttpStatus.OK);
    }
}
