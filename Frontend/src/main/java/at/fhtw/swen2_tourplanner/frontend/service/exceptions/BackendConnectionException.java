package at.fhtw.swen2_tourplanner.frontend.service.exceptions;

public class BackendConnectionException extends Exception {
    private static final String BE_ERROR_MSG = "The tour planner could not connect to the backend.";

    public BackendConnectionException() {
        super(BE_ERROR_MSG);
    }

    public BackendConnectionException(String message) {
        super(message);
    }
}
