package at.fhtw.swen2_tourplanner.backend.tour.rest;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Tour addedTour = tourService.createTour(tour);
            return new ResponseEntity<>(addedTour, HttpStatus.OK);

        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTour(@RequestBody TourDTO tour) {
        try {
            Tour updatedTour = tourService.updateTour(tour);
            return new ResponseEntity<>(updatedTour, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        if (!tours.isEmpty()) {
            return new ResponseEntity<>(tours, HttpStatus.OK);
        }
        return new ResponseEntity<>("Tours could not be found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Object> getTour(@PathVariable("id") UUID id) {
        try {
            Tour tour = tourService.getTour(id);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTour(@PathVariable("id") UUID id) {
        if (tourService.deleteTour(id)) {
            return new ResponseEntity<>("Tour deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tour deleted successfully", HttpStatus.NOT_FOUND);
    }

    //Not functional Distance and Time Request
    /*@GetMapping(value = "test")
    public ResponseEntity<Object> getTest() {
        MapQuestService mapQuestService = new MapQuestService();
        return new ResponseEntity<>(mapQuestService.getTimeAndDistance("Clarendon Blvd,Arlington,VA", "2400 S Glebe Rd, Arlington, VA"), HttpStatus.NOT_FOUND);
    }*/

}
