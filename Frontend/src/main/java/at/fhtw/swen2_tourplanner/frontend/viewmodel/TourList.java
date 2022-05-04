package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observers.Observer;
import at.fhtw.swen2_tourplanner.frontend.observers.SearchObserver;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import lombok.Generated;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TourList implements ViewModel, SearchObserver{

    @Getter
    private StringProperty newTourName;

    @Getter
    private BooleanProperty onlyFavoriteTour;

    private ObservableList<TourDTO>  baseTourList;
    @Getter
    private FilteredList<TourDTO> tourList;

    // for filtering
    private String searchText = "";
    private boolean isFavorite = false;

    public TourList() {
        newTourName = new SimpleStringProperty();
        onlyFavoriteTour = new SimpleBooleanProperty();

        onlyFavoriteTour.addListener((observable, oldValue, newValue) -> {
            isFavorite = newValue;
            this.updateFilteredListPredicate();
        });

        // ToDo BE call for tour lists
        baseTourList = FXCollections.observableArrayList();
        baseTourList.add(new TourDTO("Hallo"));
        tourList = new FilteredList<>(baseTourList);

        tourList.setPredicate(null);
    }

    public void addTour() {
        if (!this.newTourName.getValue().isEmpty()) {
            TourDTO newTour = new TourDTO(newTourName.getValue());
            baseTourList.add(newTour);
            newTourName.setValue("");
        }
    }

    public void deleteTour(UUID id) {
        int index = findIndexOfTourById(id);
        if (index >= 0) {
            baseTourList.remove(index);
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
        }
        else {
            this.searchText = s;
        }
        this.updateFilteredListPredicate();
    }

    private void updateFilteredListPredicate() {
        Predicate<TourDTO> tourPredicate1 = TourDTO::isFavorite;
        Predicate<TourDTO> tourPredicate2 = tour -> tour.getName().toLowerCase().contains(this.searchText.toLowerCase());
        // set predicate filter list
        if (this.isFavorite) {
            tourList.setPredicate(tourPredicate1.and(tourPredicate2));
        }
        else {
            tourList.setPredicate(tourPredicate2);
        }
    }
}
