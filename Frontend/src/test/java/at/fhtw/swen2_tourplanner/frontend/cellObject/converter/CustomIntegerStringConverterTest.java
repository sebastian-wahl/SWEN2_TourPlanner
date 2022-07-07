package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomIntegerStringConverterTest {
    private final CustomIntegerStringConverter converterWithBounds = new CustomIntegerStringConverter(1, 5);
    private final String testString1 = "1";
    private final String testString4 = "1d";
    private final Integer testInt = 1;

    @SneakyThrows
    @Test
    void convertFromStringSuccessful() {
        Integer converted = converterWithBounds.convertFromString(testString1);
        assertThat(converted).isEqualTo(testInt);
    }

    @SneakyThrows
    @Test
    void convertFromStringOutOfBounds() {
        String testString2 = "6";
        Integer converted = converterWithBounds.convertFromString(testString2);
        assertThat(converted).isEqualTo(5);
    }

    @SneakyThrows
    @Test
    void convertFromStringOutOfBounds2() {
        String testString3 = "-1";
        Integer converted = converterWithBounds.convertFromString(testString3);
        assertThat(converted).isEqualTo(1);
    }

    @Test
    void convertFromStringError1() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converterWithBounds.convertFromString(testString4));
        assertThat(exception.getMessage()).isEqualTo("Could not convert string \"" + testString4 + "\" to a number.");
    }

    @SneakyThrows
    @Test
    void convertToStringSuccessful() {
        String converted = converterWithBounds.convertToString(testInt);
        assertThat(converted).isEqualTo(testString1);
    }
}