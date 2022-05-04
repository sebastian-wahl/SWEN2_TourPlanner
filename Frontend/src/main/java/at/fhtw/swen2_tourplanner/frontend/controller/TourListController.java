package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourListCell;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourList;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class TourListController extends BaseController<TourList> implements Initializable {
    @FXML
    private CheckBox favoritesCheckbox;
    @FXML
    private ListView<TourDTO> tourListView;
    @FXML
    private TextField toAddTourName;
    @FXML
    private SearchbarController searchbarController;


    public TourListController(TourList tourList) {
        super(tourList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // property binding
        this.toAddTourName.textProperty().bindBidirectional(getViewModel().getNewTourName());
        this.favoritesCheckbox.selectedProperty().bindBidirectional(getViewModel().getOnlyFavoriteTour());
        this.tourListView.setItems(this.getViewModel().getTourList());
        this.tourListView.setCellFactory(tourListViewElm -> new TourListCell(this::deleteTour));

        // register search observer
        this.searchbarController.getViewModel().registerObserver(getViewModel());
    }

    public void onAddTour() {
        this.getViewModel().addTour();
    }

    private void deleteTour(UUID id) {
        this.getViewModel().deleteTour(id);
    }
}
