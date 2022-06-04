package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import javafx.util.converter.IntegerStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomIntegerStringConverter extends IntegerStringConverter implements Converter<Integer> {
    private final Logger logger = LogManager.getLogger(CustomIntegerStringConverter.class);

    @Override
    public Integer convertFromString(String s) throws ConverterException {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Integer: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }

    @Override
    public String convertToString(Integer aInt) throws ConverterException {
        try {
            return super.toString(aInt);
        } catch (NumberFormatException e) {
            logger.error("Error when converting Integer to String: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }
}
