package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
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

public class TourLogService extends Service {
    private static final String TOUR_LOG_URL = REST_URL + "/tour-log";
    private final Logger logger = LoggerFactory.getLogger(TourLogService.class);
    private final ObjectMapper o = new ObjectMapper();

    public List<TourLogDTO> getAllLogs(UUID tourId) {
        try {
            HttpURLConnection con = getConnection(TOUR_LOG_URL + "/get/" + tourId.toString());
            con.setRequestMethod(GET_METHODE);
            int status = con.getResponseCode();
            logger.info("Get all tour logs request, status: {}", status);
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

    public boolean deleteTour(TourLogDTO tourLog) {
        try {
            HttpURLConnection con = getConnection(TOUR_LOG_URL + "/delete/" + tourLog.getId().toString());
            con.setRequestMethod(DELETE_METHODE);
            int status = con.getResponseCode();
            logger.info("Delete tourlog request, status: {}", status);
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

    public Optional<TourLogDTO> addTour(TourLogDTO tourLog) {
        return addOrUpdateTour(tourLog, POST_METHODE, TOUR_LOG_URL + "/create");
    }

    public Optional<TourLogDTO> updateTour(TourLogDTO tourLog) {
        return addOrUpdateTour(tourLog, PUT_METHODE, TOUR_LOG_URL + "/update");
    }

    private Optional<TourLogDTO> addOrUpdateTour(TourLogDTO tourLog, String putMethode, String url) {
        try {
            HttpURLConnection con = getConnection(url);
            con.setRequestMethod(putMethode);
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);

            PrintStream ps = new PrintStream(con.getOutputStream());
            String json = o.writeValueAsString(tourLog);
            ps.print(json);
            ps.close();

            int status = con.getResponseCode();

            if (putMethode.equals(POST_METHODE)) {
                logger.info("Add tourLog request, status: {}", status);
            } else {
                logger.info("Update tourLog request, status: {}", status);
            }

            if (status == HttpURLConnection.HTTP_OK) {
                String content = extractStringBodyFromResponse(con);
                return Optional.of(o.readValue(content, TourLogDTO.class));
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
