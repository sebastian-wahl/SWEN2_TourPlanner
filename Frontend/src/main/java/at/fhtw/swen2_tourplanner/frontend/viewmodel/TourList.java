package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchBaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourBaseObservable;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.AddUpdateSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.DeleteSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.GetMultipleTourService;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class TourList implements ViewModel, SearchBaseObserver, UpdateTourBaseObservable, ChangeListener<Tour> {
    // logger
    private final Logger logger = LogManager.getLogger(TourList.class);

    // services
    private final TourService tourService;

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

    public TourList(TourService tourService) {
        this.tourService = tourService;
        newTourName = new SimpleStringProperty();
        onlyFavoriteTour = new SimpleBooleanProperty();

        onlyFavoriteTour.addListener((observable, oldValue, newValue) -> this.updateFilteredListPredicate());

        listviewBaseObserver = new ArrayList<>();
        baseTourList = FXCollections.observableArrayList();
        this.setBaseTourList();

        tourList = new FilteredList<>(baseTourList);
        // default filtering -> no filters set
        tourList.setPredicate(null);
    }

    private void setBaseTourList() {
        GetMultipleTourService getMultipleTourService = new GetMultipleTourService(tourService::getAllTours);
        getMultipleTourService.valueProperty().addListener(new ChangeListener<List<Tour>>() {
            @Override
            public void changed(ObservableValue<? extends List<Tour>> observableValue, List<Tour> tours, List<Tour> newValues) {
                baseTourList.addAll(newValues);
            }
        });
        getMultipleTourService.start();
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
            AddUpdateSingleTourService addUpdateSingleTourService = new AddUpdateSingleTourService(tourService::addTour, newTour);
            addUpdateSingleTourService.valueProperty().addListener(new ChangeListener<Optional<Tour>>() {
                @Override
                public void changed(ObservableValue<? extends Optional<Tour>> observableValue, Optional<Tour> tourDTO, Optional<Tour> newValue) {
                    if (newValue.isPresent()) {
                        baseTourList.add(newValue.get());
                        // reset input field and select newly added tour
                        newTourName.setValue("");
                        listViewSelectionModel.select(newValue.get());
                    }
                }
            });
            addUpdateSingleTourService.start();
        }
    }

    public void deleteTour(UUID id) {
        int index = findIndexOfTourById(id);
        if (index >= 0) {
            Tour toRemove = baseTourList.remove(index);
            DeleteSingleTourService deleteSingleTourService = new DeleteSingleTourService(tourService::deleteTour, toRemove);
            // Maybe check result?
            deleteSingleTourService.start();
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
