package at.fhtw.swen2_tourplanner.frontend.service.exceptions;

public class ApiCallTimoutException extends Exception {
    public ApiCallTimoutException(String message) {
        super("The API Call \"" + message + "\" timed out!");
    }
}
