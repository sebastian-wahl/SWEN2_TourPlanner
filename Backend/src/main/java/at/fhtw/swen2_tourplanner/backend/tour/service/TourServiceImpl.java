package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import at.fhtw.swen2_tourplanner.backend.tour.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Business Logic Executor
@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    //Service method implementations
    //calls Repository
    @Override
    public void createTour(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public void updateTour(Tour tour) throws Exception {
        /*final Long tourId = tourRepository.findById(tour.getId()).get().getId();
        if (tourId != null) {
            tourRepository.save(tour);
        } else {
            throw new Exception("Could not find tour");
        }*/
    }

    @Override
    public Tour getTour(long id) throws Exception {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            return tour.get();
        }
        throw new Exception("Could not find tour");
    }

    @Override
    public void deleteTour(long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
}
