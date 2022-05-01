package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourListCell;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourList;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController extends BaseController<TourList> implements Initializable {
    @FXML
    private CheckBox favoritesCheckbox;
    @FXML
    private ListView<TourDTO> tourListView;
    @FXML
    private TextField toAddTourName;


    public TourListController() {
        super(new TourList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tourListView.setItems(this.getViewModel().getTourList());
        this.tourListView.setCellFactory(tourListView -> new TourListCell(this::deleteTour));
    }

    public void onAddTour() {
        String name = toAddTourName.getText();
        if (!name.isEmpty()) {
            this.getViewModel().addTour(toAddTourName.getText());
            toAddTourName.setText("");
        }
    }

    private void deleteTour(Long id) {
        this.getViewModel().deleteTour(id);
    }
}
