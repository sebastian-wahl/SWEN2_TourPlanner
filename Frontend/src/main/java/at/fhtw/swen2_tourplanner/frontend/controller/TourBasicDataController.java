package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourBasicData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

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

    private FileChooser fileChooser;


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

        fileChooser = new FileChooser();
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
