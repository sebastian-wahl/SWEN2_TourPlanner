package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourBasicData;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TourBasicDataController extends BaseController<TourBasicData> {

    @FXML
    private TextField name;

    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML
    private Label distance;

    @FXML
    private TextArea description;

    @FXML
    private Button exportButton;

    @FXML
    private Button editSaveButton;

    @FXML
    private CheckBox favoriteCheckbox;


    public TourBasicDataController(TourBasicData viewModel) {
        super(viewModel);
    }

    @FXML
    public void initialize() {
        this.name.textProperty().bindBidirectional(getViewModel().getNameProperty());
        this.name.disableProperty().bind(getViewModel().getNameDisableProperty());

        this.from.textProperty().bindBidirectional(getViewModel().getFromProperty());
        this.from.disableProperty().bind(getViewModel().getFromDisableProperty());

        this.to.textProperty().bindBidirectional(getViewModel().getToProperty());
        this.to.disableProperty().bind(getViewModel().getToDisableProperty());

        this.distance.textProperty().bind(getViewModel().getDescriptionProperty());

        this.description.textProperty().bindBidirectional(getViewModel().getDescriptionProperty());
        this.description.disableProperty().bind(getViewModel().getDescriptionDisableProperty());

        this.editSaveButton.textProperty().bind(getViewModel().getEditSaveButtonTextProperty());
        this.editSaveButton.disableProperty().bind(getViewModel().getEditSaveButtonDisableProperty());
        this.favoriteCheckbox.disableProperty().bind(getViewModel().getCheckboxDisableProperty());

        this.exportButton.disableProperty().bind(getViewModel().getExportButtonDisableProperty());
    }

    public void onEditOrSave() {
        getViewModel().editOrSaveTour();
    }
}
