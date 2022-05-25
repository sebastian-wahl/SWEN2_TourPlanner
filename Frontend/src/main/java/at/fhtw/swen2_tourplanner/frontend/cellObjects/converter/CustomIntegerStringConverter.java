package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomIntegerStringConverter extends IntegerStringConverter implements Converter<Integer> {
    private final Logger logger = LoggerFactory.getLogger(CustomIntegerStringConverter.class);

    @Override
    public Integer fromString(String s) {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Integer: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String toString(Integer integer) {
        try {
            return super.toString(integer);
        } catch (NumberFormatException e) {
            logger.error("Error when converting Integer to String: {}", e.getMessage());
            throw e;
        }
    }
}
