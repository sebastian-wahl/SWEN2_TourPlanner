package at.fhtw.swen2_tourplanner.frontend.util;

import at.fhtw.swen2_tourplanner.frontend.util.exceptions.FileConvertException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileConverterTest {

    private final ClassLoader classLoader = getClass().getClassLoader();

    private final Tour tour1 = Tour.builder()
            .id(UUID.fromString("a384d852-48e6-4f2c-a268-21e075de3fce"))
            .name("Test1")
            .start("Ospelgasse 12 Wien 1200")
            .goal("Hauptallee 5 1020 Wien")
            .transportType(0)
            .tourDistance(1.71)
            .estimatedTime(LocalTime.of(0, 5, 33))
            .favorite(true)
            .routeImageName("a384d852-48e6-4f2c-a268-21e075de3fce_image.jpg")
            .childFriendliness(1)
            .popularity(1)
            .build();

    private final Tour tour2 = Tour.builder()
            .id(UUID.fromString("18e0533c-2fbc-4800-a496-c9f0692515dd"))
            .name("Tour 2")
            .start("Ospelgasse 2, 1200 Wien")
            .goal("Praterstraße 4, 1020 Wien")
            .transportType(1)
            .tourDistance(1.846)
            .estimatedTime(LocalTime.of(0, 5, 48))
            .favorite(false)
            .routeImageName("18e0533c-2fbc-4800-a496-c9f0692515dd_image.jpg")
            .childFriendliness(1)
            .popularity(1)
            .build();

    @SneakyThrows
    @Test
    void convertFileToTourSuccessful() {
        File file1 = new File(classLoader.getResource("File1.json").getFile());
        File file2 = new File(classLoader.getResource("File2.json").getFile());

        List<Tour> converted = FileConverter.convertFileToTour(List.of(file1, file2));

        assertThat(converted).contains(tour1, tour2);
    }

    @SneakyThrows
    @Test
    void convertFileToTourError1() {
        File file3 = new File(classLoader.getResource("File3.json").getFile());

        FileConvertException exception = assertThrows(FileConvertException.class, () -> FileConverter.convertFileToTour(List.of(file3)));
        assertThat(exception.getMessage()).contains("Exception while converting file " + file3.getName());
    }

    @SneakyThrows
    @Test
    void convertFileToTourError2() {
        File file4 = new File(classLoader.getResource("File4.xml").getFile());
        FileConvertException exception = assertThrows(FileConvertException.class, () -> FileConverter.convertFileToTour(List.of(file4)));
        assertThat(exception.getMessage()).contains("Exception while converting file " + file4.getName());
    }

}