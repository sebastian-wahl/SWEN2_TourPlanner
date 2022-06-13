package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourLogData;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TourLogDataController extends BaseController<TourLogData> {
    // Table and Columns
    @FXML
    TableColumn<TourLog, Integer> numberCol;
    @FXML
    TableColumn<TourLog, LocalDateTime> dateCol;
    @FXML
    TableColumn<TourLog, LocalTime> totalTimeCol;
    @FXML
    TableColumn<TourLog, Integer> difficultyCol;
    @FXML
    TableColumn<TourLog, Double> ratingCol;

    @FXML
    TableColumn<TourLog, Double> distanceCol;
    @FXML
    private TableView<TourLog> logTableView;

    @FXML
    private TextArea commentInput;
    @FXML
    private HBox toolTipHBox;

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
    private Label distanceLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Button addLogButton;
    @FXML
    private Button deleteButton;


    public TourLogDataController(TourLogData viewModel) {
        super(viewModel);
    }

    @FXML
    private void initialize() {
        logTableView.setItems(getViewModel().getTourLogList());
        getViewModel().setTableSelectionModel(logTableView.getSelectionModel());

        Tooltip tooltip = new Tooltip("Double click to edit, ESC to save");
        Tooltip.install(toolTipHBox, tooltip);

        toolTipHBox.setOnMouseClicked(getViewModel()::commentMouseClickHandler);
        commentInput.textProperty().bindBidirectional(getViewModel().getCommentTextProperty());
        commentInput.setOnKeyReleased(getViewModel()::commentKeyHandler);

        commentInput.disableProperty().bind(getViewModel().getCommentInputDisableProperty());
        addLogButton.disableProperty().bind(getViewModel().getAddLogButtonDisableProperty());
        deleteButton.disableProperty().bind(getViewModel().getDeleteLogButtonDisableProperty());
        logTableView.disableProperty().bind(getViewModel().getTableDisableProperty());

        dateLabel.textProperty().bind(getViewModel().getDateLabelProperty());
        totalTimeLabel.textProperty().bind(getViewModel().getTimeLabelProperty());
        distanceLabel.textProperty().bind(getViewModel().getDistanceLabelProperty());
        difficultyLabel.textProperty().bind(getViewModel().getDifficultyLabelProperty());
        ratingLabel.textProperty().bind(getViewModel().getRatingLabelProperty());

        this.setupTable();
    }

    public void setupTable() {
        logTableView.setEditable(true);
        logTableView.getSelectionModel().selectedItemProperty().addListener(getViewModel()::selectionChanged);

        dateCol.setSortType(TableColumn.SortType.DESCENDING);
        logTableView.getSortOrder().add(dateCol);


        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        dateCol.setOnEditCommit(getViewModel()::onEditCommitDate);
        dateCol.cellFactoryProperty().bindBidirectional(getViewModel().getDateColCellFactoryProperty());
        totalTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        totalTimeCol.setOnEditCommit(getViewModel()::onEditCommitTime);
        totalTimeCol.cellFactoryProperty().bindBidirectional(getViewModel().getTimeColCellFactoryProperty());
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        difficultyCol.setOnEditCommit(getViewModel()::onEditCommitDifficulty);
        difficultyCol.cellFactoryProperty().bindBidirectional(getViewModel().getDifficultyColCellFactoryProperty());
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.setOnEditCommit(getViewModel()::onEditCommitRating);
        ratingCol.cellFactoryProperty().bindBidirectional(getViewModel().getRatingColCellFactoryProperty());
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        distanceCol.setOnEditCommit(getViewModel()::onEditCommitDistance);
        distanceCol.cellFactoryProperty().bindBidirectional(getViewModel().getDistanceColCellFactoryProperty());
    }

    public void addTourLog() {
        getViewModel().addTourLog();
        this.logTableView.sort();
    }

    public void deleteSelectedTourLog() {
        getViewModel().deleteSelectedTourLog();
        this.logTableView.sort();
    }
}
