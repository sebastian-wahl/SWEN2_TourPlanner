package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import javafx.util.converter.LongStringConverter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLongStringConverter extends LongStringConverter implements Converter<Long> {
    @Override
    public Long convertFromString(String s) throws ConverterException {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            log.error("Error when converting String to Long: {}", e.getMessage());
            throw new ConverterException("Could not convert string \"" + s + "\" to a number.");
        }
    }

    @Override
    public String convertToString(Long aLong) throws ConverterException {
        return super.toString(aLong);
    }
}
