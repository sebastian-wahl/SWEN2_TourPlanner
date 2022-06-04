package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateTimeStringConverter implements Converter<LocalDateTime> {
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private final Logger logger = LogManager.getLogger(CustomLocalDateTimeStringConverter.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    @Override
    public String convertToString(LocalDateTime value) throws ConverterException {
        try {
            return formatter.format(value);
        } catch (DateTimeException e) {
            logger.error("Error when converting LocalDateTime to String: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }

    @Override
    public LocalDateTime convertFromString(String string) throws ConverterException {
        try {
            return LocalDateTime.parse(string, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting String to LocalDateTime: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }
}
