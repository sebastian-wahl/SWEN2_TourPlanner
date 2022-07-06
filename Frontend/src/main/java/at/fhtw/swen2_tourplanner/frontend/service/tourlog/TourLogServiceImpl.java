package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class TourLogServiceImpl implements TourLogService {
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
    public List<TourLog> getAllLogs(UUID tourId) throws BackendConnectionException {
        try {
            log.info("Get all tour logs request sent.");
            return tourLogAPI.getAllLogs(tourId).execute().body();
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"getAllTourLogs\" timed out!");
        } catch (IOException e) {
            log.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteTourLog(TourLog tourLog) throws BackendConnectionException {
        try {
            log.info("Delete tour log request sent. Deleting tour log with id {}. ", tourLog.getId());
            return tourLogAPI.deleteTourLog(tourLog.getId()).execute().isSuccessful();
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"deleteTourLog\" timed out!");
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return false;
    }

    @Override

    public Optional<TourLog> addTourLog(TourLog tourLog) throws BackendConnectionException {
        try {
            log.info("Add tour log request sent");
            return tourLogAPI.addTourLog(tourLog).execute().body();
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"addTourLog\" timed out");
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TourLog> updateTourLog(TourLog tourLog) throws BackendConnectionException {
        try {
            log.info("Update tour log request sent. Updating log with id {}", tourLog.getId());
            return tourLogAPI.updateTourLog(tourLog).execute().body();
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"updateTourLog\" timed out");
        } catch (ConnectException e) {
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return Optional.empty();
    }

    @Override
    public byte[] getTourReport(UUID tourId) throws BackendConnectionException {
        try {
            log.info("Get tour request sent. Generation tour request for tour with");
            final ResponseBody responseBody = tourLogAPI.getTourReport(tourId).execute().body();
            return responseBody != null ? responseBody.bytes() : new byte[0];
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"getTourReport\" timed out");
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return new byte[0];
    }

    @Override
    public byte[] getTourSummary() throws BackendConnectionException {
        try {
            log.info("Get tour summary sent.");
            final ResponseBody responseBody = tourLogAPI.getTourSummary().execute().body();
            return responseBody != null ? responseBody.bytes() : new byte[0];
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"getTourSummary\" timed out");
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return new byte[0];
    }

    @Override
    public Optional<Tour> getComputedTourAttributes(UUID tourId) throws BackendConnectionException {
        try {
            log.info("Update tour attributes for tour with id {}", tourId.toString());
            return tourLogAPI.getComputedTourAttributes(tourId).execute().body();
        } catch (SocketTimeoutException ex) {
            throw new BackendConnectionException("The API Call \"updateTourLog\" timed out");
        } catch (ConnectException e) {
            throw new BackendConnectionException();
        } catch (IOException e) {
            log.error(e);
        }
        return Optional.empty();
    }


}
