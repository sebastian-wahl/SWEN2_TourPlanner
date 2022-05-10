package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Dashboard;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourInfo;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class DashboardController extends BaseController<Dashboard> {

    public DashboardController(Dashboard dashboard) {
        super(dashboard);
    }

    @FXML
    public void initialize(){
    }
}
