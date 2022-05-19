package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.TourSelectObservable;
import at.fhtw.swen2_tourplanner.frontend.observer.TourSelectObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObservable;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObserver;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;

public class TourInfo implements ViewModel, TourSelectObserver, UpdateTourObserver {
    // services
    private final TourBasicData tourBasicData;

    private final TourMap tourMap;

    public TourInfo(TourBasicData tourBasicData, TourMap tourMap) {
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;
    }

    @Override
    public void update(TourDTO tourDTO, Class<?> from) {
        if (TourSelectObservable.class.isAssignableFrom(from)) {
            tourBasicData.setCurrentTour(tourDTO);
            tourMap.setCurrentTour(tourDTO);
        } else if (UpdateTourObservable.class.isAssignableFrom(from)) {
            tourMap.setCurrentTour(tourDTO);
        }
    }
}
