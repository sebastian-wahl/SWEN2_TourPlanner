package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomIntegerStringConverter extends IntegerStringConverter implements Converter<Integer> {
    private final Logger logger = LoggerFactory.getLogger(CustomIntegerStringConverter.class);

    @Override
    public Integer fromString(String s) throws NumberFormatException {
        return super.fromString(s);
    }

    @Override
    public String toString(Integer integer) throws NumberFormatException {
        return super.toString(integer);
    }

    @Override
    public Integer convertFromString(String s) throws ConverterException {
        try {
            return this.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Integer: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }

    @Override
    public String convertToString(Integer aInt) throws ConverterException {
        try {
            return this.toString(aInt);
        } catch (NumberFormatException e) {
            logger.error("Error when converting Integer to String: {}", e.getMessage());
            throw new ConverterException(e);
        }
    }
}
