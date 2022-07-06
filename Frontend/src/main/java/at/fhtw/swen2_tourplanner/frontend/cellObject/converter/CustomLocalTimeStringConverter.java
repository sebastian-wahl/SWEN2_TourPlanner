package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
public class CustomLocalTimeStringConverter implements Converter<LocalTime> {
    public static final String TIME_FORMAT = "HH:mm:ss";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    @Override
    public LocalTime convertFromString(String s) throws ConverterException {
        try {
            return LocalTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error when converting String to LocalDateTime: {}", e.getMessage());
            throw new ConverterException("Please use format: \"" + TIME_FORMAT.toUpperCase() + "\". Could not convert string \"" + s + "\" to a Time object.");
        }
    }

    @Override
    public String convertToString(LocalTime localTime) throws ConverterException {
        try {
            return formatter.format(localTime);
        } catch (DateTimeParseException e) {
            log.error("Error when converting LocalDateTime to String: {}", e.getMessage());
            throw new ConverterException("Could not convert Time \"" + localTime.toString() + "\" to a formatted string.");
        }
    }
}
