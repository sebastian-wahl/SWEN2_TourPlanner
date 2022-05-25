package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalTimeStringConverter implements Converter<LocalTime> {
    private final Logger logger = LoggerFactory.getLogger(CustomLocalTimeStringConverter.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime fromString(String s) {
        try {
            return LocalTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting String to LocalDateTime: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public String toString(LocalTime localTime) {
        try {
            return formatter.format(localTime);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting LocalDateTime to String: {}", e.getMessage());
        }
        return null;
    }
}
