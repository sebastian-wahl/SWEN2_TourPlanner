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
            tourMapQuestHelper.setMapQuestData(tour);
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
            if (locationChanged(tour, dbTour.get())) {
                tourMapQuestHelper.setMapQuestData(tour);
            }
            return new TourDTO(tourRepository.save(new Tour(tour)), tour.getRouteImage());
        } else {
            throw new BusinessException("Could not find tour");
        }

    }

    @Override
    public TourDTO getTour(UUID id) throws BusinessException {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            return tourMapQuestHelper.setRouteImage(tour.get());
        }
        throw new BusinessException("Could not find tour");
    }

    @Override
    public boolean deleteTour(UUID id) {
        try {
            tourRepository.deleteById(id);
            File imageFile = tourMapQuestHelper.getImageFile(id);
            return imageFile.delete();
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public List<TourDTO> getAllTours() {
        return tourRepository.findAll().stream().map(tourMapQuestHelper::setRouteImage).toList();
    }

    private boolean locationChanged(TourDTO tour, Tour dbTour) {
        return !tour.getStart().equals(dbTour.getStart()) || !tour.getGoal().equals(dbTour.getGoal());
    }
}
