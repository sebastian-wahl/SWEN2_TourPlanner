package at.fhtw.swen2_tourplanner.backend.mapquest.rest;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "map")
public class MapQuestController {
    private MapQuestService mapQuestService;

    @Autowired
    public MapQuestController(MapQuestService mapQuestService) {
        this.mapQuestService = mapQuestService;
    }

    @GetMapping(value = "validate/location")
    public ResponseEntity<Object> validateLocation(String address) {
        Optional<String> streetAndPostcodeOpt = mapQuestService.validateLocation(address);
        return streetAndPostcodeOpt.<ResponseEntity<Object>>map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No location found", HttpStatus.NOT_FOUND));
    }
}
