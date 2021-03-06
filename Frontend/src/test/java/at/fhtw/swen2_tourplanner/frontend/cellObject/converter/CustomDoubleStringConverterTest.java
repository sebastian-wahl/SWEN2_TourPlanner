package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomDoubleStringConverterTest {

    private final CustomDoubleStringConverter converterWithLimit = new CustomDoubleStringConverter(1, 6, 2);
    private final String testString4 = "2.fs";
    private final Double testDouble1 = 1.23;

    @SneakyThrows
    @Test
    void convertFromStringSuccessful() {
        String testString1 = "1.233";
        Double converted = converterWithLimit.convertFromString(testString1);
        assertThat(converted).isEqualTo(testDouble1);
    }

    @SneakyThrows
    @Test
    void convertFromStringBoundLimit() {
        String testString2 = "0.2";
        Double converted = converterWithLimit.convertFromString(testString2);
        assertThat(converted).isEqualTo(1);
    }

    @SneakyThrows
    @Test
    void convertFromStringBoundLimit2() {
        String testString3 = "6.01";
        Double converted = converterWithLimit.convertFromString(testString3);
        assertThat(converted).isEqualTo(6);
    }

    @SneakyThrows
    @Test
    void convertFromStringError() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converterWithLimit.convertFromString(testString4));
        assertThat(exception.getMessage()).isEqualTo("Could not convert string \"" + testString4 + "\" to a number.");
    }

    @SneakyThrows
    @Test
    void convertToString() {
        String converted = converterWithLimit.convertToString(testDouble1);
        assertThat(converted).isEqualTo("1.23");
    }
}