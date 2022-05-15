package at.fhtw.swen2_tourplanner.backend.mapquest.service;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;
import org.springframework.web.client.RestTemplate;

public class MapQuestService {
    private final static String BASE_URL = "http://www.mapquestapi.com/";
    private final static String KEY = "LHowJeyQWtehWyYnmQca5ODP0Sw8cj71";
    private final static String ROUTE_URL = BASE_URL + "directions/v2/route?key=" + KEY;
    private final static String IMAGE_URL = BASE_URL + "staticmap/v5/map?size=600,400@2x&key=" + KEY;

    public MapQuestResponse getTimeAndDistance(String from, String to) {
        final String url = ROUTE_URL + "&from=" + from + "&to=" + to;
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, MapQuestResponse.class);
    }

    public byte[] getImage(String start, String end) {
        final String url = IMAGE_URL + "&start=" + start + "&end=" + end;
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, byte[].class);
    }


}
