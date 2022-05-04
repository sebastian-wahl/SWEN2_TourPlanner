package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Searchbar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchbarController extends BaseController<Searchbar> {
    @FXML
    private TextField searchTextField;

    @FXML
    private Button clearButton;

    public SearchbarController(Searchbar searchbar) {
        super(searchbar);
    }

    @FXML
    public void initialize() {
        // set up viewModel mapping
        this.searchTextField.textProperty().bindBidirectional(getViewModel().getSearchText());
    }

    public void onClearSearch() {
        getViewModel().clearSearch();
    }

    public void onKeyReleased() {
        getViewModel().search();
    }
}
