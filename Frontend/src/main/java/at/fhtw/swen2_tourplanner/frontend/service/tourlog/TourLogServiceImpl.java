package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourLogServiceImpl implements TourLogService {
    private final Logger logger = LogManager.getLogger(TourLogServiceImpl.class);

    private final TourLogAPI tourLogAPI;

    public TourLogServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_URL)
                .addConverterFactory(JacksonConverterFactory.create(
                        JsonMapper.builder()
                                .addModule(new ParameterNamesModule())
                                .addModule(new Jdk8Module())
                                .addModule(new JavaTimeModule())
                                .build()
                ))
                .build();

        tourLogAPI = retrofit.create(TourLogAPI.class);
    }


    @Override
    public List<TourLog> getAllLogs(UUID tourId) throws ApiCallTimoutException {
        try {
            logger.info("Get all tour logs request sent.");
            return tourLogAPI.getAllLogs(tourId).execute().body();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("getAllTourLogs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteTourLog(TourLog tourLog) throws ApiCallTimoutException {
        try {
            logger.info("Delete tour log request sent. Deleting tour log with id {}. ", tourLog.getId());
            return tourLogAPI.deleteTourLog(tourLog.getId()).execute().isSuccessful();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("deleteTourLog");
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override

    public Optional<TourLog> addTourLog(TourLog tourLog) throws ApiCallTimoutException {
        try {
            logger.info("Add tour log request sent");
            return tourLogAPI.addTourLog(tourLog).execute().body();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("addTourLog");
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<TourLog> updateTourLog(TourLog tourLog) throws ApiCallTimoutException {
        try {
            logger.info("Update tour log request sent. Updating log with id {}", tourLog.getId());
            return tourLogAPI.updateTourLog(tourLog).execute().body();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("updateTourLog");
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
