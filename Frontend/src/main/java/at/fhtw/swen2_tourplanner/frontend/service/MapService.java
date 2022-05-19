package at.fhtw.swen2_tourplanner.frontend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapService extends Service {
    private static final String MAP_URL = REST_URL + "/map/image";

    private final Logger logger = LoggerFactory.getLogger(MapService.class);

    public boolean validateLocation(String address) {
        return true;
    }
}
