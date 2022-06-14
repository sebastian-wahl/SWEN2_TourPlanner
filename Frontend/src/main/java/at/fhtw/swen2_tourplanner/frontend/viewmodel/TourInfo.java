package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.StringBooleanObservable;
import at.fhtw.swen2_tourplanner.frontend.observer.StringBooleanObserver;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourInfo implements ViewModel, StringBooleanObservable {
    // models
    private final TourBasicData tourBasicData;
    private final TourMap tourMap;

    private final List<StringBooleanObserver> observerList;

    public TourInfo(TourBasicData tourBasicData, TourMap tourMap) {
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;

        this.observerList = new ArrayList<>();
    }


    public void updateFromTourEditOperation(Tour tour) {
        tourMap.setCurrentTour(tour);
    }

    public void updateFromTourList(Tour tour) {
        tourBasicData.setCurrentTour(tour);
        tourMap.setCurrentTour(tour);
    }

    @Override
    public void registerObserver(StringBooleanObserver observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserver(StringBooleanObserver observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (StringBooleanObserver observer : this.observerList) {
            observer.notify();
        }
    }
}
