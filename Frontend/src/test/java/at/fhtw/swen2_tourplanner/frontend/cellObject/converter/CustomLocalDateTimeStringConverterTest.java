package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomLocalDateTimeStringConverterTest {

    private CustomLocalDateTimeStringConverter converter = new CustomLocalDateTimeStringConverter();
    private String dateString = "12.12.2022 12:12";
    private String dateString2 = "12:12.2022 12:12";
    private LocalDateTime localDate = LocalDateTime.of(2022, 12, 12, 12, 12);

    @BeforeEach
    void setUp() {
    }

    @SneakyThrows
    @Test
    void convertToString() {
        String converted = converter.convertToString(localDate);
        assertThat(converted).isEqualTo(dateString);
    }

    @SneakyThrows
    @Test
    void convertFromStringSuccessful() {
        LocalDateTime localDateTime = converter.convertFromString(dateString);
        assertThat(localDateTime).isEqualTo(localDate);
    }

    @Test
    void convertFromStringError() {
        ConverterException exception = assertThrows(ConverterException.class, () -> converter.convertFromString(dateString2));
        assertThat(exception.getMessage()).contains("Please use format: \"DD.MM.YYYY HH:MM\".");
    }
}