package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
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

public class TourServiceImpl implements TourService {
    private final Logger logger = LogManager.getLogger(TourServiceImpl.class);

    private final TourAPI tourAPI;

    public TourServiceImpl() {
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

        tourAPI = retrofit.create(TourAPI.class);
    }

    @Override
    public List<Tour> getAllTours() throws BackendConnectionException, ApiCallTimoutException {
        try {
            logger.info("Get all tours request send.");
            List<Tour> result = tourAPI.getAllTours().execute().body();
            return result != null ? result : Collections.emptyList();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("getAllTours");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Tour> getTourById(UUID id) throws BackendConnectionException, ApiCallTimoutException {
        try {
            logger.info("Get tour by id request send.");
            return tourAPI.getTourById(id).execute().body();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("getTour");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public boolean deleteTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException {
        try {
            logger.info("Delete tour with id {}", tour.getId());
            return tourAPI.deleteTour(tour.getId()).execute().isSuccessful();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("deleteTour");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Tour> addTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException {
        try {
            logger.info("Add tour");
            return tourAPI.addTour(tour).execute().body();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("addTour");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tour> updateTour(Tour tour) throws BackendConnectionException, ApiCallTimoutException {
        try {
            logger.info("Update tour request sent. Updating tour with id {}", tour);
            return tourAPI.updateTour(tour).execute().body();
        } catch (ConnectException e) {
            logger.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("updateTour");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}