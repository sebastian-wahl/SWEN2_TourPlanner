package at.fhtw.swen2_tourplanner.backend.mapquest.service;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapLocationResponse;
import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapQuestServiceImpl implements MapQuestService {
    private final String ROUTE_URL;
    private final String IMAGE_URL;
    private final String LOCATION_URL;

    public MapQuestServiceImpl(@Value("${mapquest.route}") final String routeUrl,
                               @Value("${mapquest.image}") final String imageUrl,
                               @Value("${mapquest.location}") final String locationUrl) {
        ROUTE_URL = routeUrl;
        IMAGE_URL = imageUrl;
        LOCATION_URL = locationUrl;
    }


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
    public MapLocationResponse validateLocation(String address) {
        final String url = LOCATION_URL + "&location=" + address;
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, MapLocationResponse.class);
    }


}
