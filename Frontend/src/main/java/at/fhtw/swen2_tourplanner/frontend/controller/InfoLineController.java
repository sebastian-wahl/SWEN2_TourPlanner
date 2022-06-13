package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.InfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class InfoLineController extends BaseController<InfoLine> {
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label infoLabel;


    public InfoLineController(InfoLine viewModel) {
        super(viewModel);
    }

    @FXML
    public void initialize() {
        this.progressBar.visibleProperty().bind(getViewModel().getLoadingProperty());
        this.infoLabel.textProperty().bindBidirectional(getViewModel().getInfoTextProperty());
    }
}
