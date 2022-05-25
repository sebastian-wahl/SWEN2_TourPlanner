package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourService extends Service {
    private static final String TOUR_URL = REST_URL + "/tour";
    private final Logger logger = LoggerFactory.getLogger(TourService.class);
    private final ObjectMapper o = new ObjectMapper();


    public List<TourDTO> getAllTours() {
        try {
            HttpURLConnection con = getConnection(TOUR_URL + "/get");
            con.setRequestMethod(GET_METHODE);
            int status = con.getResponseCode();
            logger.info("Get all tours request, status: {}", status);
            if (status == HttpURLConnection.HTTP_OK) {
                String content = extractStringBodyFromResponse(con);
                logger.debug("Request content: {}", content);
                return o.readValue(content, new TypeReference<>() {
                });
            }
            con.disconnect();
            return Collections.emptyList();
        } catch (ConnectException ex) {
            logger.error("Failed to connect to the BE, no requests possible");
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<TourDTO> getTourById(UUID id) {
        try {
            HttpURLConnection con = getConnection(TOUR_URL + "/get/" + id.toString());
            con.setRequestMethod(GET_METHODE);
            int status = con.getResponseCode();
            logger.info("Get tour by id request, status: {}", status);
            if (status == HttpURLConnection.HTTP_OK) {
                String content = extractStringBodyFromResponse(con);
                logger.debug("Request content: {}", content);
                return Optional.of(o.readValue(content, TourDTO.class));
            }
            return Optional.empty();
        } catch (ConnectException ex) {
            logger.error("Failed to connect to the BE, no requests possible");
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public boolean deleteTour(TourDTO tour) {
        try {
            HttpURLConnection con = getConnection(TOUR_URL + "/delete/" + tour.getId().toString());
            con.setRequestMethod(DELETE_METHODE);
            int status = con.getResponseCode();
            logger.info("Delete tour tour request, status: {}", status);
            if (status == HttpURLConnection.HTTP_OK) {
                con.disconnect();
                return true;
            }
            return false;
        } catch (ConnectException ex) {
            logger.error("Failed to connect to the BE, no requests possible");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<TourDTO> addTour(TourDTO tour) {
        return addOrUpdateTour(tour, POST_METHODE, TOUR_URL + "/create");
    }

    public Optional<TourDTO> updateTour(TourDTO tour) {
        return addOrUpdateTour(tour, PUT_METHODE, TOUR_URL + "/update");
    }

    private Optional<TourDTO> addOrUpdateTour(TourDTO tour, String putMethode, String url) {
        try {
            HttpURLConnection con = getConnection(url);
            con.setRequestMethod(putMethode);
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);
            PrintStream ps = new PrintStream(con.getOutputStream());
            String json = o.writeValueAsString(tour);
            ps.print(json);
            ps.close();

            int status = con.getResponseCode();

            if (putMethode.equals(POST_METHODE)) {
                logger.info("Add tour tour request, status: {}", status);
            } else {
                logger.info("Update tour tour request, status: {}", status);
            }

            if (status == HttpURLConnection.HTTP_OK) {
                String content = extractStringBodyFromResponse(con);
                return Optional.of(o.readValue(content, TourDTO.class));
            }
            return Optional.empty();
        } catch (ConnectException ex) {
            logger.error("Failed to connect to the BE, no requests possible");
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}