package at.fhtw.swen2_tourplanner.backend.tour.util;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;
import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.UUID;

@Component
@Log4j2
public class TourMapQuestHelper {
    public static final String IMAGE_SUFFIX = "_image.jpg";
    public final String ABSOLUTE_IMAGE_PATH;
    private final MapQuestService mapQuestService;

    @Autowired
    public TourMapQuestHelper(MapQuestService mapQuestService, @Value("${image.path.prefix}") String[] pathValues) {
        this.mapQuestService = mapQuestService;
        ABSOLUTE_IMAGE_PATH = Paths.get(pathValues[0], pathValues[1], pathValues[2], pathValues[3], pathValues[4]).toFile().getAbsolutePath();
    }

    public byte[] getImage(String tourId) throws FileNotFoundException {
        if (tourId != null) {
            try {
                File file = new File(ABSOLUTE_IMAGE_PATH + '\\' + tourId);
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                log.error("Error while loading the image from tourId '{}'", ABSOLUTE_IMAGE_PATH + "\\" + tourId);
                throw new FileNotFoundException("Image File not found under tourId: " + ABSOLUTE_IMAGE_PATH + "\\" + tourId);
            }
        }
        return new byte[0];
    }

    public void setRouteImage(Tour tour) {
        try {
            tour.setImage(this.getImage(tour.getRouteImageName()));
        } catch (FileNotFoundException e) {
            this.setTourMapImage(tour);
        }
    }

    public File getImageFile(UUID id) {
        String fileName = id.toString() + IMAGE_SUFFIX;
        return new File(ABSOLUTE_IMAGE_PATH + '\\' + fileName);
    }

    public void setMapQuestData(Tour tour) {
        setTourMapImage(tour);
        setTourDistanceAndTime(tour);
    }

    private String saveRouteImage(UUID tourId, byte[] route) {
        File newFile = getImageFile(tourId);
        log.info("Saving route image to resources");
        try {
            if (!newFile.exists()) {
                log.info("File does not exist, creating a new one");
                new File(ABSOLUTE_IMAGE_PATH).mkdirs();
                newFile.createNewFile();
            }
            try (OutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write(route);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return newFile.getName();
    }


    private void setTourMapImage(Tour tour) {
        byte[] image = this.mapQuestService.getImage(tour.getStart(), tour.getGoal());
        String imageName = this.saveRouteImage(tour.getId(), image);
        tour.setRouteImageName(imageName);
        tour.setImage(image);
        log.info("Route image name: {}", imageName);
    }

    private void setTourDistanceAndTime(Tour tour) {
        MapQuestResponse response = mapQuestService.getTimeAndDistance(tour.getStart(), tour.getGoal());
        final double distance = response.getRoute().getDistance();
        final LocalTime time = response.getRoute().getFormattedTime();
        tour.setTourDistance(distance);
        tour.setEstimatedTime(time);
        log.info("New Distance: {}", distance);
        log.info("New Time: {}", time);
    }

}
