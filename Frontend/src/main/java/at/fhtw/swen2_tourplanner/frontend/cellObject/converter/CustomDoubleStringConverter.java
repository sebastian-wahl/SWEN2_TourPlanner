package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import javafx.util.converter.DoubleStringConverter;
import lombok.extern.log4j.Log4j2;

import java.text.DecimalFormat;

@Log4j2
public class CustomDoubleStringConverter extends DoubleStringConverter implements Converter<Double> {

    private final double lowerBound;
    private final double upperBound;

    private final DecimalFormat df;
    private final boolean limitDigits;

    public CustomDoubleStringConverter() {
        this(Double.MIN_VALUE, Double.MAX_VALUE, -1);
    }

    public CustomDoubleStringConverter(double lower, double upper, int limitDigits) {
        this.lowerBound = lower;
        this.upperBound = upper;
        if (limitDigits > 0) {
            this.limitDigits = true;
            df = new DecimalFormat("##." + "0".repeat(limitDigits));
        } else {
            df = null;
            this.limitDigits = false;
        }
    }

    private Double fromStringAdapted(String s) throws NumberFormatException {
        try {
            double number = super.fromString(s);
            if (limitDigits) {
                number = super.fromString(df.format(number).replace(",", "."));
            }
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
            throw e;
        }
    }

    private boolean isNumberInBound(double number) {
        return lowerBound <= number && upperBound >= number;
    }

    private boolean isBoundsActive() {
        return lowerBound > 0 && upperBound > 0;
    }

    private String toStringAdapted(Double aDouble) throws NumberFormatException {
        try {
            return super.toString(aDouble);
        } catch (NumberFormatException e) {
            log.error("Error when converting Integer to String: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Double convertFromString(String s) throws ConverterException {
        try {
            return this.fromStringAdapted(s);
        } catch (NumberFormatException e) {
            throw new ConverterException("Could not convert string \"" + s + "\" to a number.");
        }
    }

    @Override
    public String convertToString(Double aDouble) throws ConverterException {
        try {
            return this.toStringAdapted(aDouble);
        } catch (NumberFormatException e) {
            throw new ConverterException("Could not convert number " + aDouble + " to a string.");
        }
    }
}
