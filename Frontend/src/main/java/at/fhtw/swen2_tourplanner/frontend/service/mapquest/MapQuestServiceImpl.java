package at.fhtw.swen2_tourplanner.frontend.service.mapquest;

import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Log4j2
public class MapQuestServiceImpl implements MapQuestService {
    private final MapQuestAPI mapQuestAPI;

    public MapQuestServiceImpl() {
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

        mapQuestAPI = retrofit.create(MapQuestAPI.class);
    }

    @Override
    public boolean validateInput(String location) throws BackendConnectionException, ApiCallTimoutException {
        try {
            log.info("Validate location request send for location '{}'.", location);
            return Boolean.TRUE.equals(mapQuestAPI.validateLocation(location).execute().body());
        } catch (ConnectException e) {
            log.error("Failed to connect to BE!");
            throw new BackendConnectionException();
        } catch (SocketTimeoutException ex) {
            throw new ApiCallTimoutException("getAllTours");
        } catch (IOException e) {
            log.error(e);
        }
        return false;
    }
}
