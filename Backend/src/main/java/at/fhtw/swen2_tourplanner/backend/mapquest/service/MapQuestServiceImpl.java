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

    @Value("${mapquest.url}")
    private String BASE_URL;
    @Value("${mapquest.key}")
    private String KEYL;
    @Value("${mapquest.route}")
    private String ROUTE_URL;
    @Value("${mapquest.image}")
    private String IMAGE_URL;
    @Value("${mapquest.location}")
    private String LOCATION_URL;

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
