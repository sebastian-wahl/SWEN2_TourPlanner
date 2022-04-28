package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Dashboard;
import javafx.fxml.FXML;

public class DashboardController extends BaseController<Dashboard> {
    @FXML
    public MenubarController menubarController;

    public DashboardController() {
        super(new Dashboard());
    }

    @FXML
    public void initialize(){
    }
}
