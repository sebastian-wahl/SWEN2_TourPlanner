package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.repo.TourRepository;
import at.fhtw.swen2_tourplanner.backend.tour.util.TourMapQuestHelper;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Business Logic Executor
@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapQuestHelper tourMapQuestHelper;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, TourMapQuestHelper tourMapQuestHelper) {
        this.tourRepository = tourRepository;
        this.tourMapQuestHelper = tourMapQuestHelper;
    }

    @Override
    public TourDTO createTour(TourDTO tour) throws BusinessException {
        if (tour.getId() == null) {
            Tour dbTour = tourRepository.save(new Tour(tour));
            if (isStartAndGoalPresent(tour)) {
                tourMapQuestHelper.setMapQuestData(dbTour);
            }
            return new TourDTO(tourRepository.save(dbTour), dbTour.getImage());
        } else {
            throw new BusinessException("Tour already exists");
        }
    }

    private boolean isStartAndGoalPresent(TourDTO tour) {
        return tour.getStart() != null && !tour.getStart().isEmpty() && tour.getGoal() != null && !tour.getGoal().isEmpty();
    }

    private boolean isStartAndGoalPresent(Tour tour) {
        return tour.getStart() != null && !tour.getStart().isEmpty() && tour.getGoal() != null && !tour.getGoal().isEmpty();
    }

    @Override
    public TourDTO updateTour(TourDTO tour) throws BusinessException {
        if (tour.getId() == null) {
            throw new BusinessException("No Tour Id supplied");
        }
        Tour dbTour = new Tour(tour);
        Optional<Tour> oldTour = tourRepository.findById(tour.getId());
        if (oldTour.isPresent()) {
            if (locationChanged(tour, oldTour.get())) {
                tourMapQuestHelper.setMapQuestData(dbTour);
            }
            return new TourDTO(tourRepository.save(dbTour), dbTour.getImage());
        } else {
            throw new BusinessException("Could not find tour");
        }

    }

    @Override
    public TourDTO getTour(UUID id) throws BusinessException {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            tourMapQuestHelper.setRouteImage(tour.get());
            return new TourDTO(tour.get());
        }
        throw new BusinessException("Could not find tour");
    }

    @Override
    public boolean deleteTour(UUID id) throws IOException {
        try {
            tourRepository.deleteById(id);
            File imageFile = tourMapQuestHelper.getImageFile(id);
            Files.deleteIfExists(imageFile.toPath());
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public List<TourDTO> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        List<TourDTO> tourDTOS = new ArrayList<>();
        tours.forEach(tour -> {
            final boolean secondIteration = false;
            tourDTOS.add(this.createTDOReadImageFile(tour, secondIteration));
        });
        return tourDTOS;
    }

    private TourDTO createTDOReadImageFile(Tour tour, final boolean secondIteration) {
        try {
            if (this.isStartAndGoalPresent(tour)) {
                return new TourDTO(tour, Files.readAllBytes(tourMapQuestHelper.getImageFile(tour.getId()).toPath()));
            }
            return new TourDTO(tour);
        } catch (IOException ex) {
            if (secondIteration) {
                throw new BusinessException("Could not find Image");
            }
            tourMapQuestHelper.setMapQuestData(tour);
            return this.createTDOReadImageFile(tour, true);
        }
    }

    private boolean locationChanged(TourDTO tour, Tour dbTour) {
        return !tour.getStart().equals(dbTour.getStart()) || !tour.getGoal().equals(dbTour.getGoal());
    }
}
