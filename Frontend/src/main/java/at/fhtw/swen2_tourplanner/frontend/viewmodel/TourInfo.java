package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observers.TourSelectObserver;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;

public class TourInfo implements ViewModel, TourSelectObserver {
    private final TourBasicData tourBasicData;

    public TourInfo(TourBasicData tourBasicData) {
        this.tourBasicData = tourBasicData;
    }
    @Override
    public void update(TourDTO tourDTO) {
        tourBasicData.setCurrentTour(tourDTO);
    }
}
