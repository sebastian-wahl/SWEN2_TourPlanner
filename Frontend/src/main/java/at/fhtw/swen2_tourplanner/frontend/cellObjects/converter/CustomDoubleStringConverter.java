package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import javafx.util.converter.DoubleStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomDoubleStringConverter extends DoubleStringConverter implements Converter<Double> {
    private final Logger logger = LogManager.getLogger(CustomDoubleStringConverter.class);

    @Override
    public Double fromString(String s) throws NumberFormatException {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Integer: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String toString(Double aDouble) throws NumberFormatException {
        try {
            return super.toString(aDouble);
        } catch (NumberFormatException e) {
            logger.error("Error when converting Integer to String: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Double convertFromString(String s) throws ConverterException {
        try {
            return this.fromString(s);
        } catch (NumberFormatException e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public String convertToString(Double aDouble) throws ConverterException {
        try {
            return this.toString(aDouble);
        } catch (NumberFormatException e) {
            throw new ConverterException(e);
        }
    }
}
