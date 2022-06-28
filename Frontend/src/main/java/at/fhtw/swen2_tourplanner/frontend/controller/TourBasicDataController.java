package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourBasicData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;

public class TourBasicDataController extends BaseController<TourBasicData> {

    private final FileChooser fileChooser;
    @FXML
    public HBox fromHbox;
    @FXML
    public HBox toHbox;
    @FXML
    public Button validateFromButton;
    @FXML
    public Button validateToButton;
    @FXML
    private TextField name;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private Label distance;
    @FXML
    private Label estimatedTime;
    @FXML
    private TextArea description;
    @FXML
    private Button exportSummaryButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button editSaveButton;
    @FXML
    private CheckBox favoriteCheckbox;
    @FXML
    private ComboBox<String> transportType;


    public TourBasicDataController(TourBasicData viewModel) {
        super(viewModel);
        fileChooser = new FileChooser();
    }

    @FXML
    public void initialize() {
        this.name.textProperty().bindBidirectional(getViewModel().getNameProperty());
        this.name.disableProperty().bind(getViewModel().getNameDisableProperty());

        this.from.textProperty().bindBidirectional(getViewModel().getFromProperty());
        this.from.disableProperty().bind(getViewModel().getFromDisableProperty());

        this.to.textProperty().bindBidirectional(getViewModel().getToProperty());
        this.to.disableProperty().bind(getViewModel().getToDisableProperty());

        this.distance.textProperty().bind(getViewModel().getDistanceProperty());

        this.estimatedTime.textProperty().bind(getViewModel().getEstimatedTimeProperty());

        this.description.textProperty().bindBidirectional(getViewModel().getDescriptionProperty());
        this.description.disableProperty().bind(getViewModel().getDescriptionDisableProperty());

        this.editSaveButton.textProperty().bind(getViewModel().getEditSaveButtonTextProperty());
        this.editSaveButton.disableProperty().bind(getViewModel().getEditSaveButtonDisableProperty());

        this.favoriteCheckbox.selectedProperty().bindBidirectional(getViewModel().getFavoriteCheckboxProperty());
        this.favoriteCheckbox.disableProperty().bind(getViewModel().getCheckboxDisableProperty());

        this.transportType.disableProperty().bindBidirectional(getViewModel().getTransportTypeDisableProperty());
        this.transportType.itemsProperty().bindBidirectional(getViewModel().getTransportTypeItemsProperty());
        this.transportType.valueProperty().bindBidirectional(getViewModel().getTransportTypeSelectedItemProperty());
        this.exportButton.disableProperty().bind(getViewModel().getExportButtonDisableProperty());
        this.exportSummaryButton.disableProperty().bind(getViewModel().getExportSummaryButtonDisableProperty());

        validateToButton.visibleProperty().bind(getViewModel().getValidateToButtonVisibilityProperty());
        validateToButton.managedProperty().bind(validateToButton.visibleProperty());

        validateFromButton.visibleProperty().bind(getViewModel().getValidateFromButtonVisibilityProperty());
        validateFromButton.managedProperty().bind(validateFromButton.visibleProperty());


        validateToButton.disableProperty().bind(getViewModel().getValidateToButtonDisableProperty());
        validateFromButton.disableProperty().bind(getViewModel().getValidateFromButtonDisableProperty());
    }

    public void validateTo(ActionEvent actionEvent) {
        this.getViewModel().validateToInput();
    }

    public void validateFrom(ActionEvent actionEvent) {
        this.getViewModel().validateFromInput();
    }

    public void onEditOrSave() {
        getViewModel().editOrSaveTour();
    }

    public void exportSummary() {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf"));
        fileChooser.setTitle("Save Tour Report");
        File saveToFile = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
        if (saveToFile != null) {
            this.getViewModel().exportTourReport(saveToFile);
        }
    }

    public void exportTour() {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json", "*.JSON"));
        fileChooser.setTitle("Save Tour Export");
        File saveToFile = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
        if (saveToFile != null) {
            this.getViewModel().exportTour(saveToFile);
        }
    }
}
