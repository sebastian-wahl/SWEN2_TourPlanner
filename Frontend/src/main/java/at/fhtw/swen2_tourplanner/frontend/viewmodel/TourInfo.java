package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;

public class TourInfo implements ViewModel {
    // services
    private final TourBasicData tourBasicData;

    private final TourMap tourMap;

    public TourInfo(TourBasicData tourBasicData, TourMap tourMap) {
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;
    }

    public void updateFromTourEditOperation(TourDTO tourDTO) {
        tourMap.setCurrentTour(tourDTO);
    }

    public void updateFromTourList(TourDTO tourDTO) {
        tourBasicData.setCurrentTour(tourDTO);
        tourMap.setCurrentTour(tourDTO);
    }
}
