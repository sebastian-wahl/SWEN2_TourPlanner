package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.mapquest.service.MapQuestService;
import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.repo.TourRepository;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//Business Logic Executor
@Service
public class TourServiceImpl implements TourService {
    @Value("${image.path.prefix}")
    private String[] pathValues;
    // Map constants
    private final String ABSOLUTE_IMAGE_PATH = Paths.get(pathValues[0], pathValues[1], pathValues[2], pathValues[3], pathValues[4]).toFile().getAbsolutePath();
    private static String IMAGE_NAME = "_image";

    private final Logger logger = LoggerFactory.getLogger(TourServiceImpl.class);
    private final TourRepository tourRepository;
    private final MapQuestService mapQuestService;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, MapQuestService mapQuestService) {
        this.tourRepository = tourRepository;
        this.mapQuestService = mapQuestService;

    }

    @Override
    public TourDTO createTour(TourDTO tour) throws BusinessException {
        if (tour.getId() == null) {
            return new TourDTO(tourRepository.save(new Tour(tour)), new byte[0]);
        } else {
            throw new BusinessException("Tour already exists");
        }
    }

    @Override
    public TourDTO updateTour(TourDTO tour) throws BusinessException {
        if (tour.getId() == null) {
            throw new BusinessException("No Tour Id supplied");
        }
        Optional<Tour> dbTour = tourRepository.findById(tour.getId());
        if (dbTour.isPresent()) {
            if (!tour.getStart().equals(dbTour.get().getStart()) || !tour.getGoal().equals(dbTour.get().getGoal())) {
                // reload map
                reloadTourMap(tour);
            }
            return new TourDTO(tourRepository.save(new Tour(tour)), tour.getRouteImage());
        } else {
            throw new BusinessException("Could not find tour");
        }
    }

    private void reloadTourMap(TourDTO tour) {
        byte[] image = this.mapQuestService.getImage(tour.getStart(), tour.getGoal());
        String imageName = this.saveRouteImage(tour.getId(), image);
        tour.setRouteImageName(imageName);
        tour.setRouteImage(image);
        logger.info("Route image name: {}", imageName);
    }

    public String saveRouteImage(UUID tourId, byte[] route) {
        String fileName = tourId.toString() + IMAGE_NAME;
        File newFile = new File(ABSOLUTE_IMAGE_PATH + "/" + fileName + ".jpg");
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

        return fileName;
    }

    public byte[] getImageFromFile(String path) {
        if (path != null) {
            try {
                File file = new File(ABSOLUTE_IMAGE_PATH + "/" + path);
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                logger.error("Error while loading the image from path '{}'", ABSOLUTE_IMAGE_PATH + "/" + path);
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    @Override
    public TourDTO getTour(UUID id) throws BusinessException {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            return new TourDTO(tour.get(), this.getImageFromFile(tour.get().getRouteImageName()));
        }
        throw new BusinessException("Could not find tour");
    }

    @Override
    public boolean deleteTour(UUID id) {
        try {
            tourRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public List<TourDTO> getAllTours() {
        return tourRepository.findAll().stream().map(tour -> new TourDTO(tour, this.getImageFromFile(tour.getRouteImageName()))).collect(Collectors.toList());
    }
}
