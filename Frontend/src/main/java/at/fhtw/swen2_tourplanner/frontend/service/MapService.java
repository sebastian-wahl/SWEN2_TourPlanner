package at.fhtw.swen2_tourplanner.frontend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static at.fhtw.swen2_tourplanner.frontend.service.Service.REST_URL;

public class MapService {
    private static final String MAP_URL = REST_URL + "/validate/location";

    private final Logger logger = LogManager.getLogger(MapService.class);

    public boolean validateLocation(String address) {
        return true;
    }
}
