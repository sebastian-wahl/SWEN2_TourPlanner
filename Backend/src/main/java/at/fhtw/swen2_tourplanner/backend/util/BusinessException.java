package at.fhtw.swen2_tourplanner.backend.util;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
