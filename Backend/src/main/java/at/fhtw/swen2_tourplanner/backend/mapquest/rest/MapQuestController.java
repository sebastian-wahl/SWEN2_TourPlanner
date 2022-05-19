package at.fhtw.swen2_tourplanner.backend.mapquest.rest;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "map")
public class MapQuestController {
    private final TourService tourService;
    private MapQuestService mapQuestService;

    @Autowired
    public MapQuestController(TourService tourService, MapQuestService mapQuestService) {
        this.tourService = tourService;
        this.mapQuestService = mapQuestService;
    }

    //Functional image request
    @GetMapping(value = "image")
    public ResponseEntity<byte[]> getMapImage(UUID tourId) {
        TourDTO tourDTO = this.tourService.getTour(tourId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(
                mapQuestService.getImage(tourDTO.getStart(), tourDTO.getGoal()),
                headers,
                HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "validate/location")
    public ResponseEntity<Object> validateLocation(String address) {
        Optional<String> streetAndPostcodeOpt = mapQuestService.validateLocation(address);
        return streetAndPostcodeOpt.<ResponseEntity<Object>>map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No location found", HttpStatus.NOT_FOUND));
    }
}
