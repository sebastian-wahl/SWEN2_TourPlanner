package at.fhtw.swen2_tourplanner.frontend.service.mapquest;

import at.fhtw.swen2_tourplanner.frontend.service.Service;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;

public interface MapQuestService extends Service {
    boolean validateInput(String location) throws BackendConnectionException, ApiCallTimoutException;
}
