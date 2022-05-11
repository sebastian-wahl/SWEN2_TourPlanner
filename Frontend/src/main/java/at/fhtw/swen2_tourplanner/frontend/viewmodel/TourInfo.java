package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.TourSelectObserver;
import at.fhtw.swen2_tourplanner.frontend.service.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;

public class TourInfo implements ViewModel, TourSelectObserver {
    // services
    private final TourService tourService;
    private final TourBasicData tourBasicData;

    public TourInfo(TourBasicData tourBasicData, TourService tourService) {
        this.tourBasicData = tourBasicData;
        this.tourService = tourService;
    }

    @Override
    public void update(TourDTO tourDTO) {
        tourBasicData.setCurrentTour(tourDTO);
    }
}
