package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import javafx.util.converter.IntegerStringConverter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomIntegerStringConverter extends IntegerStringConverter implements Converter<Integer> {
    private final int lowerBound;
    private final int upperBound;

    public CustomIntegerStringConverter(int lower, int upper) {
        this.lowerBound = lower;
        this.upperBound = upper;
    }

    @Override
    public Integer convertFromString(String s) throws ConverterException {
        try {
            int number = super.fromString(s);
            if (isBoundsActive()) {
                if (isNumberInBound(number)) {
                    return number;
                } else {
                    return number > upperBound ? upperBound : lowerBound;
                }
            }
            return number;
        } catch (NumberFormatException e) {
            log.error("Error when converting String to Integer: {}", e.getMessage());
            throw new ConverterException("Could not convert string \"" + s + "\" to a number.");
        }
    }

    private boolean isNumberInBound(int number) {
        return lowerBound <= number && upperBound >= number;
    }

    private boolean isBoundsActive() {
        return lowerBound > 0 && upperBound > 0;
    }

    @Override
    public String convertToString(Integer aInt) throws ConverterException {
        try {
            return super.toString(aInt);
        } catch (NumberFormatException e) {
            log.error("Error when converting Integer to String: {}", e.getMessage());
            throw new ConverterException("Could not convert number " + aInt + " to a string.");
        }
    }
}
