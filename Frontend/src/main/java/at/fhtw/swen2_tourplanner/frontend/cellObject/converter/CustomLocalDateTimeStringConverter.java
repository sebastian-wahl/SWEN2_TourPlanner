package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.extern.log4j.Log4j2;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
public class CustomLocalDateTimeStringConverter implements Converter<LocalDateTime> {
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    @Override
    public String convertToString(LocalDateTime value) throws ConverterException {
        try {
            return formatter.format(value);
        } catch (DateTimeException e) {
            log.error("Error when converting LocalDateTime to String: {}", e.getMessage());
            throw new ConverterException("Could not convert Date \"" + value.toString() + "\" to a formatted string.");
        }
    }

    @Override
    public LocalDateTime convertFromString(String string) throws ConverterException {
        try {
            return LocalDateTime.parse(string, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error when converting String to LocalDateTime: {}", e.getMessage());
            throw new ConverterException("Please use format: \"" + DATE_TIME_FORMAT.toUpperCase() + "\". Could not convert string \"" + string + "\" to a Date object.");
        }
    }
}
