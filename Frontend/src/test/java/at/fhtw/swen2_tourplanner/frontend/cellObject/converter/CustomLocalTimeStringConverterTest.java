package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomLocalTimeStringConverterTest {

    private CustomLocalTimeStringConverter converter = new CustomLocalTimeStringConverter();

    private String timeString = "12:12:12";
    private String timeString2 = "111:12:11";
    private String timeString3 = "42:12:100";

    private LocalTime time = LocalTime.of(12, 12, 12);

    @SneakyThrows
    @Test
    void convertFromStringSuccessful() {
        LocalTime localTime = converter.convertFromString(timeString);
        assertThat(localTime).isEqualTo(time);
    }

    @Test
    void convertFromStringError() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converter.convertFromString(timeString2));
        assertThat(exception.getMessage()).contains("Please use format: \"HH:MM:SS\".");

    }

    @Test
    void convertFromStringError2() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converter.convertFromString(timeString3));
        assertThat(exception.getMessage()).contains("Please use format: \"HH:MM:SS\".");
    }

    @SneakyThrows
    @Test
    void convertToString() {
        String converted = converter.convertToString(time);
        assertThat(converted).isEqualTo(timeString);
    }
}