package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Searchbar;
import javafx.fxml.FXML;

public class TourSearchbarController extends BaseController<Searchbar> {
    @FXML
    private SearchbarBaseController searchbarBaseController;

    public TourSearchbarController(Searchbar searchbar) {
        super(searchbar);
    }

    @FXML
    public void initialize() {
        searchbarBaseController.setClearSearchImpl(this::onClearSearch);
        searchbarBaseController.setKeyReleasedImpl(this::onKeyReleased);
        // set up viewModel mapping
        searchbarBaseController.getSearchTextField().textProperty().bindBidirectional(getViewModel().getSearchText());
        searchbarBaseController.getSearchTextField().setPromptText("Filter by Tour Name...");
    }

    public void onClearSearch() {
        getViewModel().clearSearch();
    }

    public void onKeyReleased() {
        getViewModel().search();
    }
}
