package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourMap;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class TourMapController extends BaseController<TourMap> {
    @FXML
    private ImageView imageView;

    public TourMapController(TourMap viewModel) {
        super(viewModel);
    }

    @FXML
    public void initialize() {
        imageView.imageProperty().bindBidirectional(getViewModel().getImageProperty());
    }
}
