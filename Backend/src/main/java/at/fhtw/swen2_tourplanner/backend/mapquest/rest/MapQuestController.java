package at.fhtw.swen2_tourplanner.backend.mapquest.rest;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "map")
public class MapQuestController {
    private final MapQuestService mapQuestService;

    @Autowired
    public MapQuestController(MapQuestService mapQuestService) {
        this.mapQuestService = mapQuestService;
    }

    @GetMapping(value = "validate/location/{address}")
    public ResponseEntity<Object> validateLocation(@PathVariable("address") String address) {
        return new ResponseEntity<>(mapQuestService.validateLocation(address), HttpStatus.OK);
    }
}
