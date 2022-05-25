package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateTimeStringConverter implements Converter<LocalDateTime> {
    private final Logger logger = LoggerFactory.getLogger(CustomLocalDateTimeStringConverter.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public String toString(LocalDateTime value) {
        try {
            return formatter.format(value);
        } catch (DateTimeException e) {
            logger.error("Error when converting LocalDateTime to String: {}", e.getMessage());
            throw e;
        }
    }

    public LocalDateTime fromString(String string) {
        try {
            return LocalDateTime.parse(string, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting String to LocalDateTime: {}", e.getMessage());
            throw e;
        }
    }
}
