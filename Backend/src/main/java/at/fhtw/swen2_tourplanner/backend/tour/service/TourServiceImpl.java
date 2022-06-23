package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.repo.TourRepository;
import at.fhtw.swen2_tourplanner.backend.tour.util.TourMapQuestHelper;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(TourMapQuestHelper.class);

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
            if (tourHasStartAndGoal(tour)) {
                tourMapQuestHelper.setMapQuestData(dbTour);
            }
            return new TourDTO(tourRepository.save(dbTour), dbTour.getImage());
        } else {
            throw new BusinessException("Tour already exists");
        }
    }

    @Override
    public List<TourDTO> createTours(List<TourDTO> tourList) throws BusinessException {
        List<TourDTO> out = new ArrayList<>();
        for (TourDTO tour : tourList) {
            out.add(this.createTour(tour));
        }
        return out;
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
            tourMapQuestHelper.setRouteImage(tour);
            tourDTOS.add(new TourDTO(tour));
        });
        return tourDTOS;
    }

    private boolean locationChanged(TourDTO tour, Tour dbTour) {
        return !tour.getStart().equals(dbTour.getStart()) || !tour.getGoal().equals(dbTour.getGoal());
    }

    private boolean tourHasStartAndGoal(TourDTO tour) {
        return tour.getStart() != null && !tour.getStart().isEmpty() && tour.getGoal() != null && !tour.getGoal().isEmpty();
    }

}
