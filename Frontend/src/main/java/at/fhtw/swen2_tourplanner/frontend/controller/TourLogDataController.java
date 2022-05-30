package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourLogData;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

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

        Tooltip tooltip = new Tooltip("Double click to edit, Enter to save, ESC to exit");
        Tooltip.install(toolTipHBox, tooltip);

        toolTipHBox.setOnMouseClicked(getViewModel()::commentMouseClickHandler);
        //getViewModel().setCommentObservableList(commentInput.getParagraphs());
        commentInput.textProperty().bindBidirectional(getViewModel().getCommentTextProperty());
        commentInput.setOnKeyReleased(getViewModel()::commentKeyHandler);

        commentInput.disableProperty().bindBidirectional(getViewModel().getCommentInputDisableProperty());
        addLogButton.disableProperty().bindBidirectional(getViewModel().getAddLogButtonDisableProperty());
        deleteButton.disableProperty().bindBidirectional(getViewModel().getDeleteLogButtonDisableProperty());
        logTableView.disableProperty().bindBidirectional(getViewModel().getTableDisableProperty());

        dateLabel.textProperty().bindBidirectional(getViewModel().getDateLabelProperty());
        totalTimeLabel.textProperty().bindBidirectional(getViewModel().getTimeLabelProperty());
        difficultyLabel.textProperty().bindBidirectional(getViewModel().getDifficultyLabelProperty());
        ratingLabel.textProperty().bindBidirectional(getViewModel().getRatingLabelProperty());

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
