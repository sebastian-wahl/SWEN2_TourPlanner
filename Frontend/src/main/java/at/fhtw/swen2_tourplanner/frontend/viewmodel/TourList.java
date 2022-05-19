package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.Observer;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.TourSelectObservable;
import at.fhtw.swen2_tourplanner.frontend.service.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class TourList implements ViewModel, SearchObserver, TourSelectObservable, ChangeListener<TourDTO> {
    // services
    private final TourService tourService;

    // View Properties
    @Getter
    private final StringProperty newTourName;

    @Getter
    private final BooleanProperty onlyFavoriteTour;
    private final ObservableList<TourDTO> baseTourList;
    @Getter
    private final FilteredList<TourDTO> tourList;
    // list click observer list
    private final List<Observer<TourDTO>> listviewObserver;
    private MultipleSelectionModel<TourDTO> listViewSelectionModel;
    // for filtering
    private String searchText = "";
    private TourDTO selectedTour;

    public TourList(TourService tourService) {
        this.tourService = tourService;
        newTourName = new SimpleStringProperty();
        onlyFavoriteTour = new SimpleBooleanProperty();

        onlyFavoriteTour.addListener((observable, oldValue, newValue) -> this.updateFilteredListPredicate());

        listviewObserver = new ArrayList<>();

        baseTourList = FXCollections.observableArrayList(tourService.getAllTours());
        tourList = new FilteredList<>(baseTourList);
        // default filtering -> no filters set
        tourList.setPredicate(null);
    }

    public void setListViewSelectionModel(MultipleSelectionModel<TourDTO> listViewSelectionModel) {
        this.listViewSelectionModel = listViewSelectionModel;
        // init selection
        if (!baseTourList.isEmpty()) {
            selectedTour = baseTourList.get(0);
            listViewSelectionModel.select(selectedTour);
        }
    }

    public void addTour() {
        if (this.newTourName.getValue() != null && !this.newTourName.getValue().isEmpty()) {
            TourDTO newTour = new TourDTO(newTourName.getValue());
            Optional<TourDTO> optionalTour = tourService.addTour(newTour);
            if (optionalTour.isPresent()) {
                baseTourList.add(optionalTour.get());
                // reset input field and select newly added tour
                newTourName.setValue("");
                listViewSelectionModel.select(optionalTour.get());
            }

        }
    }

    public void deleteTour(UUID id) {
        int index = findIndexOfTourById(id);
        if (index >= 0) {
            tourService.deleteTour(baseTourList.remove(index));
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
    public void update(String s, Class<?> from) {
        if (s == null || s.isEmpty()) {
            this.searchText = "";
        } else {
            this.searchText = s;
        }
        this.updateFilteredListPredicate();
    }

    private void updateFilteredListPredicate() {
        Predicate<TourDTO> tourPredicate1 = TourDTO::isFavorite;
        Predicate<TourDTO> tourPredicate2 = tour -> tour.getName().toLowerCase().contains(this.searchText.toLowerCase());
        // set predicate filter list
        if (this.onlyFavoriteTour.get()) {
            tourList.setPredicate(tourPredicate1.and(tourPredicate2));
        } else {
            tourList.setPredicate(tourPredicate2);
        }
    }

    @Override
    public void changed(ObservableValue<? extends TourDTO> observableValue, TourDTO tourDTO, TourDTO t1) {
        this.selectedTour = t1;
        Platform.runLater(this::notifyObservers);
    }

    @Override
    public void registerObserver(Observer<TourDTO> observer) {
        this.listviewObserver.add(observer);
    }

    @Override
    public void removeObserver(Observer<TourDTO> observer) {
        this.listviewObserver.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<TourDTO> observer : listviewObserver) {
            observer.update(this.selectedTour, this.getClass());
        }
    }
}
