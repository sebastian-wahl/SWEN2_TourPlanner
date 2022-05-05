package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.repo.TourRepository;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Business Logic Executor
@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public void createTour(Tour tour) throws BusinessException {
        if (tour.getId() == null) {
            tourRepository.save(tour);
        } else {
            throw new BusinessException("Tour already exists");
        }
    }

    @Override
    public void updateTour(Tour tour) throws BusinessException {
        if (tour.getId() == null) {
            throw new BusinessException("No Tour Id supplied");
        } else if (tourRepository.findById(tour.getId()).isPresent()) {
            tourRepository.save(tour);
        } else {
            throw new BusinessException("Could not find tour");
        }
    }

    @Override
    public Tour getTour(UUID id) throws BusinessException {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            return tour.get();
        }
        throw new BusinessException("Could not find tour");
    }

    @Override
    public void deleteTour(UUID id) {
        tourRepository.deleteById(id);
    }

    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
}
