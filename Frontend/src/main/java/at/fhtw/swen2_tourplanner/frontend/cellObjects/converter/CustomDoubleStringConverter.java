package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import javafx.util.converter.DoubleStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDoubleStringConverter extends DoubleStringConverter implements Converter<Double> {
    private final Logger logger = LoggerFactory.getLogger(CustomDoubleStringConverter.class);

    @Override
    public Double fromString(String s) {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Integer: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String toString(Double aDouble) {
        try {
            return super.toString(aDouble);
        } catch (NumberFormatException e) {
            logger.error("Error when converting Integer to String: {}", e.getMessage());
            throw e;
        }
    }
}
