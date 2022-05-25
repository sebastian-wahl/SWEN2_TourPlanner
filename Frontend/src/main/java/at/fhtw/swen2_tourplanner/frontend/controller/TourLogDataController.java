package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourLogData;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TourLogDataController extends BaseController<TourLogData> {
    // Table and Columns
    @FXML
    TableColumn<TourLogDTO, Integer> numberCol;
    @FXML
    TableColumn<TourLogDTO, LocalDateTime> dateCol;
    @FXML
    TableColumn<TourLogDTO, LocalTime> totalTimeCol;
    @FXML
    TableColumn<TourLogDTO, Integer> difficultyCol;
    @FXML
    TableColumn<TourLogDTO, Double> ratingCol;
    @FXML
    private TableView<TourLogDTO> logTableView;

    @FXML
    private TextArea commentInput;
    // Labels
    @FXML
    private Label tourNumberLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label difficultyLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Label dateLabel;


    public TourLogDataController(TourLogData viewModel) {
        super(viewModel);
    }

    @FXML
    private void initialize() {
        logTableView.setItems(getViewModel().getTourLogList());
        this.setupTable();

    }

    public void setupTable() {
        logTableView.setEditable(true);
        logTableView.getSelectionModel().selectedItemProperty().addListener(getViewModel()::selectionChanged);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        dateCol.cellFactoryProperty().bindBidirectional(getViewModel().getDateColCellFactoryProperty());
        totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        totalTimeCol.cellFactoryProperty().bindBidirectional(getViewModel().getTimeColCellFactoryProperty());
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        difficultyCol.cellFactoryProperty().bindBidirectional(getViewModel().getDifficultyColCellFactoryProperty());
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.cellFactoryProperty().bindBidirectional(getViewModel().getRatingColCellFactoryProperty());
        //TextFieldTableCell.forTableColumn();
    }

    public void addTourLog() {
        System.out.println("Test");

    }

    public void deleteTourLog() {

    }
}
