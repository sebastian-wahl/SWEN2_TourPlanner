package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class TourInfoController extends BaseController<TourInfo> {

    public TourInfoController(TourInfo tourInfo) {
        super(tourInfo);
    }

    @FXML
    public void initialize(){
    }
}
