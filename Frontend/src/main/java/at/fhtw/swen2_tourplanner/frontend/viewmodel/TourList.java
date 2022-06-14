package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.listener.AddListener;
import at.fhtw.swen2_tourplanner.frontend.listener.DeleteListener;
import at.fhtw.swen2_tourplanner.frontend.listener.TourGetListener;
import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.StringObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObservable;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.MultipleSelectionModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TourList implements ViewModel, StringObserver, UpdateTourObservable, ChangeListener<Tour> {
    // logger
    private final Logger logger = LogManager.getLogger(TourList.class);

    // single Listeners
    @Setter
    private DeleteListener<Tour> tourDeleteListener;
    @Setter
    private AddListener<Tour> tourAddListener;

    private TourGetListener tourGetListener;

    // View Properties
    @Getter
    private final StringProperty newTourName;

    @Getter
    private final BooleanProperty onlyFavoriteTour;
    private final ObservableList<Tour> baseTourList;
    @Getter
    private final FilteredList<Tour> tourList;
    // list click observer list
    private final List<BaseObserver<Tour>> listviewBaseObserver;
    private MultipleSelectionModel<Tour> listViewSelectionModel;
    // for filtering
    private String searchText = "";
    private Tour selectedTour;

    public void setTourGetListener(TourGetListener tourGetListener) {
        this.tourGetListener = tourGetListener;
        this.getTours();
    }

    public TourList() {
        newTourName = new SimpleStringProperty();
        onlyFavoriteTour = new SimpleBooleanProperty();

        onlyFavoriteTour.addListener((observable, oldValue, newValue) -> this.updateFilteredListPredicate());

        listviewBaseObserver = new ArrayList<>();
        baseTourList = FXCollections.observableArrayList();

        tourList = new FilteredList<>(baseTourList);
        // default filtering -> no filters set
        tourList.setPredicate(null);
    }

    private void getTours() {
        this.tourGetListener.get();
    }

    public void getTourSuccessful(List<Tour> tourList) {
        baseTourList.addAll(tourList);
    }

    public void setListViewSelectionModel(MultipleSelectionModel<Tour> listViewSelectionModel) {
        this.listViewSelectionModel = listViewSelectionModel;
        // init selection
        if (!baseTourList.isEmpty()) {
            selectedTour = baseTourList.get(0);
            listViewSelectionModel.select(selectedTour);
        }
    }

    public void addTour() {
        if (this.newTourName.getValue() != null && !this.newTourName.getValue().isEmpty()) {
            Tour newTour = new Tour(newTourName.getValue());
            this.tourAddListener.addTour(newTour);
            newTourName.setValue("");
        }
    }

    public void addTourSuccessful(Tour addedTour) {
        this.baseTourList.add(addedTour);
        this.listViewSelectionModel.select(addedTour);
    }

    public void updateTourSuccessful(Tour updatedTour) {
        int index = findIndexOfTourById(updatedTour.getId());
        this.baseTourList.remove(index);
        this.baseTourList.add(index, updatedTour);
        this.listViewSelectionModel.select(updatedTour);
    }

    public void deleteTourSuccessful(Tour tour) {
        int index = findIndexOfTourById(tour.getId());
        baseTourList.remove(index);
    }

    public void deleteTour(UUID id) {
        int index = findIndexOfTourById(id);
        if (index >= 0) {
            this.tourDeleteListener.deleteTour(baseTourList.get(index));
        }
    }

    private int findIndexOfTourById(UUID id) {
        for (int i = 0; i < baseTourList.size(); i++) {
            if (baseTourList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param s search text
     */
    @Override
    public void update(String s) {
        if (s == null || s.isEmpty()) {
            this.searchText = "";
        } else {
            this.searchText = s;
        }
        this.updateFilteredListPredicate();
    }

    private void updateFilteredListPredicate() {
        Predicate<Tour> tourPredicate1 = Tour::isFavorite;
        Predicate<Tour> tourPredicate2 = tour -> tour.getName().toLowerCase().contains(this.searchText.toLowerCase());
        // set predicate filter list
        if (this.onlyFavoriteTour.get()) {
            tourList.setPredicate(tourPredicate1.and(tourPredicate2));
        } else {
            tourList.setPredicate(tourPredicate2);
        }
    }

    @Override
    public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
        this.selectedTour = t1;
        Platform.runLater(this::notifyObservers);
    }

    @Override
    public void registerObserver(BaseObserver<Tour> baseObserver) {
        this.listviewBaseObserver.add(baseObserver);
    }

    @Override
    public void removeObserver(BaseObserver<Tour> baseObserver) {
        this.listviewBaseObserver.remove(baseObserver);
    }

    @Override
    public void notifyObservers() {
        for (BaseObserver<Tour> baseObserver : listviewBaseObserver) {
            baseObserver.update(this.selectedTour);
        }
    }
}
