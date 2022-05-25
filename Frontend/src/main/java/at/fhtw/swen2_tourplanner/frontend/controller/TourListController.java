package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourListCell;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchBaseObservable;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourList;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.UUID;

public class TourListController extends BaseController<TourList> {
    @FXML
    private CheckBox favoritesCheckbox;
    @FXML
    private ListView<TourDTO> tourListView;
    @FXML
    private TextField toAddTourName;


    public TourListController(TourList tourList, SearchBaseObservable searchObservable) {
        super(tourList);
        // ToDo do in create methode
        searchObservable.registerObserver(tourList);
    }

    @FXML
    public void initialize() {
        // property binding
        this.toAddTourName.textProperty().bindBidirectional(getViewModel().getNewTourName());
        this.favoritesCheckbox.selectedProperty().bindBidirectional(getViewModel().getOnlyFavoriteTour());
        // add change listener to list and select first
        this.tourListView.getSelectionModel().selectedItemProperty().addListener(getViewModel());
        getViewModel().setListViewSelectionModel(this.tourListView.getSelectionModel());
        this.tourListView.setItems(this.getViewModel().getTourList());
        this.tourListView.setCellFactory(tourListViewElm -> new TourListCell(this::deleteTour));

        // focus list view
        // Platform.runLater -> run after view is created
        Platform.runLater(() -> tourListView.requestFocus());
    }

    public void onAddTour() {
        this.getViewModel().addTour();
    }

    private void deleteTour(UUID id) {
        this.getViewModel().deleteTour(id);
    }
}
