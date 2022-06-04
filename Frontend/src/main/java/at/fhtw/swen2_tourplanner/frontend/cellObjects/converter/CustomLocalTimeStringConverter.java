package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalTimeStringConverter implements Converter<LocalTime> {
    public static final String TIME_FORMAT = "HH:mm:ss";
    private final Logger logger = LogManager.getLogger(CustomLocalTimeStringConverter.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    @Override
    public LocalTime convertFromString(String s) throws ConverterException {
        try {
            return LocalTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting String to LocalDateTime: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }

    @Override
    public String convertToString(LocalTime localTime) throws ConverterException {
        try {
            return formatter.format(localTime);
        } catch (DateTimeParseException e) {
            logger.error("Error when converting LocalDateTime to String: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }
}
