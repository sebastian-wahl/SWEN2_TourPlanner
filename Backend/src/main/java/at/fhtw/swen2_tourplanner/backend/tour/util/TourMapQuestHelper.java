package at.fhtw.swen2_tourplanner.backend.tour.util;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;
import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.UUID;

@Component
public class TourMapQuestHelper {
    private final String IMAGE_NAME = "_image";
    private final Logger logger = LoggerFactory.getLogger(TourMapQuestHelper.class);
    private final MapQuestService mapQuestService;
    private final String ABSOLUTE_IMAGE_PATH;

    @Autowired
    public TourMapQuestHelper(MapQuestService mapQuestService, @Value("${image.path.prefix}") String[] pathValues) {
        this.mapQuestService = mapQuestService;
        ABSOLUTE_IMAGE_PATH = Paths.get(pathValues[0], pathValues[1], pathValues[2], pathValues[3], pathValues[4]).toFile().getAbsolutePath();
    }

    public String saveRouteImage(UUID tourId, byte[] route) {
        File newFile = getImageFile(tourId);
        logger.info("Saving route image to resources");
        try {
            if (!newFile.exists()) {
                logger.info("File does not exists, create a new one");
                newFile.createNewFile();
            }
            try (OutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write(route);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile.getName();
    }

    public byte[] getImageFromFile(String path) throws FileNotFoundException {
        if (path != null) {
            try {
                File file = new File(ABSOLUTE_IMAGE_PATH + "\\" + path);
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                logger.error("Error while loading the image from path '{}'", ABSOLUTE_IMAGE_PATH + "/" + path);
                throw new FileNotFoundException("Image File not found under path: " + ABSOLUTE_IMAGE_PATH + "/" + path);
            }
        }
        return new byte[0];
    }

    public TourDTO setRouteImage(Tour tour) {
        TourDTO out = new TourDTO(tour, new byte[0]);
        try {
            out.setRouteImage(this.getImageFromFile(out.getRouteImageName()));
        } catch (FileNotFoundException e) {
            this.setTourMapImage(out);
        }
        return out;
    }

    public File getImageFile(UUID id) {
        String fileName = id.toString() + IMAGE_NAME;
        return new File(ABSOLUTE_IMAGE_PATH + "\\" + fileName + ".jpg");
    }

    public void setMapQuestData(TourDTO tour) {
        setTourMapImage(tour);
        setTourDistanceAndTime(tour);
    }

    private void setTourMapImage(TourDTO tour) {
        byte[] image = this.mapQuestService.getImage(tour.getStart(), tour.getGoal());
        String imageName = this.saveRouteImage(tour.getId(), image);
        tour.setRouteImageName(imageName);
        tour.setRouteImage(image);
        logger.info("Route image name: {}", imageName);
    }

    private void setTourDistanceAndTime(TourDTO tour) {
        MapQuestResponse response = mapQuestService.getTimeAndDistance(tour.getStart(), tour.getGoal());
        final long distance = (long) response.getRoute().getDistance();
        final LocalTime time = response.getRoute().getFormattedTime();
        tour.setTourDistance(distance);
        tour.setEstimatedTime(time);
        logger.info("New Distance: {}", distance);
        logger.info("New Time: {}", time);
    }

}
