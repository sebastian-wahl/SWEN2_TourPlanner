package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Searchbar;
import javafx.fxml.FXML;

public class TourLogSearchbarController extends BaseController<Searchbar> {

    @FXML
    private SearchbarBaseController searchbarBaseController;

    public TourLogSearchbarController(Searchbar viewModel) {
        super(viewModel);
    }

    @FXML
    public void initialize() {
        searchbarBaseController.setClearSearchImpl(this::onClearSearch);
        searchbarBaseController.setKeyReleasedImpl(this::onKeyReleased);
        // set up viewModel mapping
        searchbarBaseController.getSearchTextField().textProperty().bindBidirectional(getViewModel().getSearchText());

        searchbarBaseController.getSearchTextField().setPromptText("Filter by Start Date and Time...");

        // disable properties
        searchbarBaseController.getSearchTextField().disableProperty().bind(getViewModel().getInputDisableProperty());
        searchbarBaseController.getClearButton().disableProperty().bind(getViewModel().getButtonDisableProperty());

    }

    public void onClearSearch() {
        getViewModel().clearSearch();
    }

    public void onKeyReleased() {
        getViewModel().search();
    }
}
