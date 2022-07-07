package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomLongStringConverterTest {

    private final CustomLongStringConverter converter = new CustomLongStringConverter();
    private final String testString2 = "1lf";
    private final String testString3 = "kdsfm";

    @SneakyThrows
    @Test
    void convertFromStringSuccessful() {
        String testString = "1";
        Long converted = converter.convertFromString(testString);
        assertThat(converted).isEqualTo(1);
    }

    @SneakyThrows
    @Test
    void convertFromStringError1() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converter.convertFromString(testString2));
        assertThat(exception.getMessage()).isEqualTo("Could not convert string \"" + testString2 + "\" to a number.");
    }

    @SneakyThrows
    @Test
    void convertFromStringError2() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converter.convertFromString(testString3));
        assertThat(exception.getMessage()).isEqualTo("Could not convert string \"" + testString3 + "\" to a number.");

    }
}