package at.fhtw.swen2_tourplanner.backend.mapquest.service;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapLocationResponse;
import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MapQuestServiceImpl implements MapQuestService {
    private static final String BASE_URL = "http://www.mapquestapi.com/";
    private static final String KEY = "LHowJeyQWtehWyYnmQca5ODP0Sw8cj71";
    private static final String ROUTE_URL = BASE_URL + "directions/v2/route?key=" + KEY;
    private static final String IMAGE_URL = BASE_URL + "staticmap/v5/map?size=600,400@2x&key=" + KEY;
    private final static String LOCATION_URL = BASE_URL + "geocoding/v1/address?key=" + KEY;
    private final Logger logger = LoggerFactory.getLogger(MapQuestServiceImpl.class);


    @Override
    public MapQuestResponse getTimeAndDistance(String from, String to) {
        final String url = ROUTE_URL + "&from=" + from + "&to=" + to;
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, MapQuestResponse.class);
    }

    @Override
    public byte[] getImage(String start, String end) {
        final String url = IMAGE_URL + "&start=" + start + "&end=" + end;
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }

    @Override
    public Optional<String> validateLocation(String address) {
        final String url = LOCATION_URL + "&location=" + address;
        final RestTemplate restTemplate = new RestTemplate();

        MapLocationResponse mapLocationResponse = restTemplate.getForObject(url, MapLocationResponse.class);
        return Optional.empty();
    }


}
