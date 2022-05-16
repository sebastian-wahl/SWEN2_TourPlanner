package at.fhtw.swen2_tourplanner.backend.mapquest.rest;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "map")
public class MapQuestController {
    //Functional image request
    @GetMapping(value = "image")
    public ResponseEntity<byte[]> getMapImage(UUID tourId) {
        MapQuestService mapQuestService = new MapQuestService();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(
                mapQuestService.getImage("Clarendon Blvd,Arlington,VA", "2400 S Glebe Rd, Arlington, VA"),
                headers,
                HttpStatus.NOT_FOUND);
    }
}
