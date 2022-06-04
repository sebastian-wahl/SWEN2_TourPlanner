package at.fhtw.swen2_tourplanner.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

public class SearchbarBaseController {

    @Setter
    private Runnable clearSearchImpl;

    @Setter
    private Runnable keyReleasedImpl;

    @FXML
    @Getter
    private TextField searchTextField;

    @FXML
    @Getter
    private Button clearButton;

    public void onClearSearch() {
        clearSearchImpl.run();
    }

    public void onKeyReleased() {
        keyReleasedImpl.run();
    }
}
