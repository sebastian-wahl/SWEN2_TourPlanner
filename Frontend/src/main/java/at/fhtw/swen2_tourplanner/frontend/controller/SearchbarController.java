package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.Searchbar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SearchbarController extends BaseController<Searchbar> {
    @FXML
    private TextField searchTextField;

    @FXML
    private Button clearButton;

    public SearchbarController() {
        super(new Searchbar());
    }

    @FXML
    public void initialize() {
        //Image img = new ImageView();
        //ImageView icon = new ImageView(Objects.requireNonNull(getClass().getResource("/icons/icons8-plus-math-16.png")).toExternalForm());
        //.setPreserveRatio(true);
        //clearButton.setGraphic(icon);
    }

    public void clearSearch() {
        this.searchTextField.setText("");
    }
}
