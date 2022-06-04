package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import javafx.util.converter.LongStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLongStringConverter extends LongStringConverter implements Converter<Long> {
    private final Logger logger = LogManager.getLogger(CustomDoubleStringConverter.class);

    @Override
    public Long convertFromString(String s) throws ConverterException {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            logger.error("Error when converting String to Long: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String convertToString(Long aLong) throws ConverterException {
        return super.toString(aLong);
    }
}
