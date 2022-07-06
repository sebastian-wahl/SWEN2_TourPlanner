package at.fhtw.swen2_tourplanner.frontend.service.exceptions;

public class BackendConnectionException extends Exception {
    private static final String ERROR_MSG = "The tour planner could not connect to the backend.";

    public BackendConnectionException() {
        super(ERROR_MSG);
    }

    public BackendConnectionException(String message) {
        super(message);
    }
}
