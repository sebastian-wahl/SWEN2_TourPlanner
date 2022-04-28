package at.fhtw.swen2_tourplanner.swen2_tourplanner.controller;

import at.fhtw.swen2_tourplanner.swen2_tourplanner.viewmodel.Dashboard;
import javafx.fxml.FXML;

public class DashboardController extends BaseController<Dashboard> {
    public DashboardController() {
        super(new Dashboard());
    }

    @FXML
    public void initialize(){
    }
}
