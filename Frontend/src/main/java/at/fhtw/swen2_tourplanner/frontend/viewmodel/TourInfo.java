package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

public class TourInfo implements ViewModel {
    // models
    private final TourBasicData tourBasicData;
    private final TourMap tourMap;

    public TourInfo(TourBasicData tourBasicData, TourMap tourMap) {
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;
    }


    public void updateFromTourEditOperation(Tour tour) {
        tourMap.setCurrentTour(tour);
    }

    public void updateFromTourList(Tour tour) {
        tourBasicData.setCurrentTour(tour);
        tourMap.setCurrentTour(tour);
    }
}
