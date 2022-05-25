package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchBaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourBaseObservable;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.AddUpdateSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.DeleteSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.GetMultipleTourService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class TourList implements ViewModel, SearchBaseObserver, UpdateTourBaseObservable, ChangeListener<TourDTO> {
    // logger
    private final Logger logger = LoggerFactory.getLogger(TourList.class);

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
    private final List<BaseObserver<TourDTO>> listviewBaseObserver;
    private MultipleSelectionModel<TourDTO> listViewSelectionModel;
    // for filtering
    private String searchText = "";
    private TourDTO selectedTour;

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
        getMultipleTourService.valueProperty().addListener(new ChangeListener<List<TourDTO>>() {
            @Override
            public void changed(ObservableValue<? extends List<TourDTO>> observableValue, List<TourDTO> tourDTOS, List<TourDTO> newValues) {
                baseTourList.addAll(newValues);
            }
        });
        getMultipleTourService.start();
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
            AddUpdateSingleTourService addUpdateSingleTourService = new AddUpdateSingleTourService(tourService::addTour, newTour);
            addUpdateSingleTourService.valueProperty().addListener(new ChangeListener<Optional<TourDTO>>() {
                @Override
                public void changed(ObservableValue<? extends Optional<TourDTO>> observableValue, Optional<TourDTO> tourDTO, Optional<TourDTO> newValue) {
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
            TourDTO toRemove = baseTourList.remove(index);
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
    public void registerObserver(BaseObserver<TourDTO> baseObserver) {
        this.listviewBaseObserver.add(baseObserver);
    }

    @Override
    public void removeObserver(BaseObserver<TourDTO> baseObserver) {
        this.listviewBaseObserver.remove(baseObserver);
    }

    @Override
    public void notifyObservers() {
        for (BaseObserver<TourDTO> baseObserver : listviewBaseObserver) {
            baseObserver.update(this.selectedTour);
        }
    }
}
