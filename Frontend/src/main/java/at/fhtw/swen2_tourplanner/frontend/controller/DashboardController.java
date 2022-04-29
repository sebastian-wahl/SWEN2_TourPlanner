package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class DashboardController extends BaseController<Dashboard> {
    @FXML
    public MenubarController menubarController;
    @FXML
    public MenuBar menubar;

    public DashboardController() {
        super(new Dashboard());
    }

    @FXML
    public void initialize(){
    }
}
