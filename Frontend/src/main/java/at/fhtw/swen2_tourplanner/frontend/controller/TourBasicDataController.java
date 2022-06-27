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
    private final Button validateButton1;
    private final Button validateButton2;
    @FXML
    public HBox fromHbox;
    @FXML
    public HBox toHbox;
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
        validateButton1 = new Button("Validate");
        validateButton2 = new Button("Validate");

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


        validateButton1.setPrefHeight(25.0);
        validateButton1.setPrefWidth(240.0);

        validateButton2.setPrefHeight(25.0);
        validateButton2.setPrefWidth(240.0);

        validateButton1.setOnAction(this::validateFrom);
        validateButton2.setOnAction(this::validateTo);
    }

    private void validateTo(ActionEvent actionEvent) {
        this.getViewModel().validateToInput();
    }

    private void validateFrom(ActionEvent actionEvent) {
        this.getViewModel().validateFromInput();
    }

    public void onEditOrSave() {
        if (this.fromHbox.getChildren().contains(validateButton1)) {
            this.fromHbox.getChildren().remove(validateButton1);
            this.toHbox.getChildren().remove(validateButton2);
        } else {
            this.fromHbox.getChildren().add(validateButton1);
            this.toHbox.getChildren().add(validateButton2);
        }
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
