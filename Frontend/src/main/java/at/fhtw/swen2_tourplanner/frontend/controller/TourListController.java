package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class TourListController extends BaseController<TourList>{
    @FXML
    private CheckBox favoritesCheckbox;

    public TourListController() {
        super(new TourList());
    }

    public void handleAddTour() {

    }
}
