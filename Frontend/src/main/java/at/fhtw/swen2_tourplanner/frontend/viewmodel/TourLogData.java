package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObject.TourLogTableCell;
import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomDoubleStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomIntegerStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomLocalDateTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.listener.AddListener;
import at.fhtw.swen2_tourplanner.frontend.listener.DeleteListener;
import at.fhtw.swen2_tourplanner.frontend.listener.TourLogGetListener;
import at.fhtw.swen2_tourplanner.frontend.listener.UpdateListener;
import at.fhtw.swen2_tourplanner.frontend.observer.StringObserver;
import at.fhtw.swen2_tourplanner.frontend.util.PredicateGenerator;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TourLogData implements ViewModel, StringObserver {
    // other view models
    private final Searchbar tourLogSearchbar;
    private final InfoLine infoLine;

    @Getter
    private final FilteredList<TourLog> tourLogList;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLog, LocalDateTime>, TableCell<TourLog, LocalDateTime>>> dateColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLog, LocalTime>, TableCell<TourLog, LocalTime>>> timeColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLog, Integer>, TableCell<TourLog, Integer>>> difficultyColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLog, Double>, TableCell<TourLog, Double>>> ratingColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLog, Double>, TableCell<TourLog, Double>>> distanceColCellFactoryProperty;
    // label properties
    @Getter
    private final ObjectProperty<String> dateLabelProperty;
    @Getter
    private final ObjectProperty<String> timeLabelProperty;
    @Getter
    private final ObjectProperty<String> distanceLabelProperty;
    @Getter
    private final ObjectProperty<String> ratingLabelProperty;
    @Getter
    private final ObjectProperty<String> difficultyLabelProperty;
    @Getter
    private final StringProperty commentTextProperty;
    // disable properties
    @Getter
    private final BooleanProperty commentInputDisableProperty;
    @Getter
    private final BooleanProperty addLogButtonDisableProperty;
    @Getter
    private final BooleanProperty deleteLogButtonDisableProperty;
    @Getter
    private final BooleanProperty tableDisableProperty;
    private final ObservableList<TourLog> baseList;
    private final SortedList<TourLog> sortedList;
    // single listeners
    @Setter
    private TourLogGetListener getTourLogListener;
    @Setter
    private AddListener<TourLog> addTourLogListener;
    @Setter
    private UpdateListener<TourLog> updateTourLogListener;
    @Setter
    private DeleteListener<TourLog> deleteTourLogListener;

    private Tour currentTour;
    private TourLog selectedTourLog;

    @Setter
    private TableView.TableViewSelectionModel<TourLog> tableSelectionModel;

    public TourLogData(Searchbar tourLogSearchbar, InfoLine infoLine) {
        this.tourLogSearchbar = tourLogSearchbar;
        this.infoLine = infoLine;

        // default disable tourlog searchbar
        this.tourLogSearchbar.disableSearchbar();

        baseList = FXCollections.observableArrayList();
        sortedList = setUpSortedList();

        tourLogList = new FilteredList<>(sortedList);
        // default filtering -> no filters set
        tourLogList.setPredicate(null);


        // Properties
        dateColCellFactoryProperty = new SimpleObjectProperty<>();
        dateColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalDateTimeStringConverter(), this::updateInfoLine, CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT.toUpperCase()));


        timeColCellFactoryProperty = new SimpleObjectProperty<>();
        timeColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalTimeStringConverter(), this::updateInfoLine, CustomLocalTimeStringConverter.TIME_FORMAT.toUpperCase()));

        difficultyColCellFactoryProperty = new SimpleObjectProperty<>();
        difficultyColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomIntegerStringConverter(1, 3), this::updateInfoLine, "1 - 3"));

        ratingColCellFactoryProperty = new SimpleObjectProperty<>();
        ratingColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomDoubleStringConverter(1.0, 10.0, 1), this::updateInfoLine, "1.0 - 10.0"));

        distanceColCellFactoryProperty = new SimpleObjectProperty<>();
        distanceColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomDoubleStringConverter(), this::updateInfoLine));

        commentInputDisableProperty = new SimpleBooleanProperty(true);
        addLogButtonDisableProperty = new SimpleBooleanProperty(true);
        deleteLogButtonDisableProperty = new SimpleBooleanProperty(true);
        tableDisableProperty = new SimpleBooleanProperty(true);

        // labels
        dateLabelProperty = new SimpleObjectProperty<>();
        timeLabelProperty = new SimpleObjectProperty<>();
        distanceLabelProperty = new SimpleObjectProperty<>();
        difficultyLabelProperty = new SimpleObjectProperty<>();
        ratingLabelProperty = new SimpleObjectProperty<>();

        commentTextProperty = new SimpleStringProperty();
    }

    private void updateInfoLine(String text) {
        this.infoLine.setErrorText(text);
    }

    private SortedList<TourLog> setUpSortedList() {
        return new SortedList<>(baseList, getSortComparator());
    }

    private Comparator<TourLog> getSortComparator() {
        return (TourLog log1, TourLog log2) -> {
            if (log1.getDateTime() == null) {
                return -1;
            }
            if (log2.getDateTime() == null) {
                return 1;
            }
            if (log1.getDateTime().isAfter(log2.getDateTime())) {
                return -1;
            } else if (log1.getDateTime().isBefore(log2.getDateTime())) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    public void commentMouseClickHandler(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                if (this.selectedTourLog != null) {
                    this.commentInputDisableProperty.setValue(false);
                }
            }
        }
    }

    public void commentKeyHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            // save comment, update tour log
            this.selectedTourLog.setComment(commentTextProperty.getValue());
            this.updateTourLog(this.selectedTourLog);
            this.commentInputDisableProperty.setValue(true);
        }

    }

    public void onEditCommitDate(TableColumn.CellEditEvent<TourLog, LocalDateTime> event) {
        if (event.getNewValue() != null) {
            TablePosition<TourLog, LocalDateTime> position = event.getTablePosition();
            int row = position.getRow();
            // update log element & update tour
            this.sortedList.get(row).setDateTime(event.getNewValue());
            this.updateTourLog(this.sortedList.get(row));
        }
    }

    public void onEditCommitTime(TableColumn.CellEditEvent<TourLog, LocalTime> event) {
        if (event.getNewValue() != null) {
            TablePosition<TourLog, LocalTime> position = event.getTablePosition();
            int row = position.getRow();
            // update log element & update tour
            this.sortedList.get(row).setTotalTime(event.getNewValue());
            this.updateTourLog(this.sortedList.get(row));
        }
    }

    public void onEditCommitDifficulty(TableColumn.CellEditEvent<TourLog, Integer> event) {
        if (event.getNewValue() != null) {
            TablePosition<TourLog, Integer> position = event.getTablePosition();
            int row = position.getRow();
            // update log element & update tour
            this.sortedList.get(row).setDifficulty(event.getNewValue());
            this.updateTourLog(this.sortedList.get(row));
        }
    }

    public void onEditCommitRating(TableColumn.CellEditEvent<TourLog, Double> event) {
        if (event.getNewValue() != null) {
            TablePosition<TourLog, Double> position = event.getTablePosition();
            int row = position.getRow();
            // update log element & update tour
            this.sortedList.get(row).setRating(event.getNewValue());
            this.updateTourLog(this.sortedList.get(row));
        }
    }

    public void onEditCommitDistance(TableColumn.CellEditEvent<TourLog, Double> event) {
        if (event.getNewValue() != null) {
            TablePosition<TourLog, Double> position = event.getTablePosition();
            int row = position.getRow();
            // update log element & update tour
            this.sortedList.get(row).setDistance(event.getNewValue());
            this.updateTourLog(this.sortedList.get(row));
        }
    }

    public void updateFromTourList(Tour tour) {
        resetTourLogValueLabels();
        setCurrentTour(tour);
    }

    private void setCurrentTour(Tour tour) {
        this.currentTour = tour;
        if (this.currentTour != null) {
            this.activateFields();
            this.tourLogSearchbar.enableSearchbar();
            this.getAllLogs();
        } else {
            this.deactivateFields();
            this.tourLogSearchbar.disableSearchbar();
        }
    }

    private void deactivateFields() {
        this.setFieldsDisabled(true);
    }

    private void activateFields() {
        this.setFieldsDisabled(false);
    }

    private void setFieldsDisabled(boolean disabled) {
        // only disable field (activated through double click)
        if (disabled) {
            commentInputDisableProperty.setValue(true);
        }

        addLogButtonDisableProperty.setValue(disabled);
        deleteLogButtonDisableProperty.setValue(disabled);
        tableDisableProperty.setValue(disabled);
    }

    public void selectionChanged(ObservableValue<? extends TourLog> observableValue, TourLog oldValue, TourLog newValue) {
        selectedTourLog = newValue;
        if (selectedTourLog != null) {
            setTourLogValuesIntoLabels();
        } else {
            resetTourLogValueLabels();
        }
    }

    private void setTourLogValuesIntoLabels() {
        dateLabelProperty.setValue(selectedTourLog.getDateTime() != null ?
                selectedTourLog.getDateTime().format(DateTimeFormatter.ofPattern(CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT)) + " (DD.MM.YYY HH:MM)" : "00.00.0000 00:00 (DD.MM.YYY HH:MM)");
        timeLabelProperty.setValue(selectedTourLog.getTotalTime() != null ?
                selectedTourLog.getTotalTime().format(DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT)) + " (HH:MM:SS)" : "00:00:00 (HH:MM:SS)");
        distanceLabelProperty.setValue(selectedTourLog.getDistance() + " km");
        difficultyLabelProperty.setValue(selectedTourLog.getDifficulty() + "/3");
        ratingLabelProperty.setValue(selectedTourLog.getRating() + "/10.0");
        commentTextProperty.setValue(Objects.requireNonNullElse(selectedTourLog.getComment(), ""));
    }

    private void resetTourLogValueLabels() {
        dateLabelProperty.setValue("");
        timeLabelProperty.setValue("");
        distanceLabelProperty.setValue("");
        difficultyLabelProperty.setValue("");
        ratingLabelProperty.setValue("");
        commentTextProperty.setValue("");
    }


    /**
     * Update from searchbar
     *
     * @param s date time search string
     */
    @Override
    public void update(String s) {
        if (s != null && !s.isEmpty()) {
            tourLogList.setPredicate(PredicateGenerator.getTourLogPredicate(s));
        } else {
            tourLogList.setPredicate(null);
        }

    }

    /* ----------------------------------------- API request methods ------------------------------------------------- */

    private void getAllLogs() {
        this.getTourLogListener.get(currentTour.getId());
    }

    public void getAllLogsSuccessful(List<TourLog> tourLogList) {
        baseList.clear();
        baseList.addAll(tourLogList);
    }

    public void addTourLog() {
        TourLog toAdd = new TourLog(currentTour);
        toAdd.setDistance(currentTour.getTourDistance());
        this.addTourLogListener.addTour(toAdd);
    }

    public void addTourLogSuccessful(TourLog addedTourLog) {
        baseList.add(addedTourLog);
        tableSelectionModel.select(addedTourLog);
    }

    private void updateTourLog(TourLog toUpdate) {
        this.updateTourLogListener.updateTour(toUpdate);
    }

    public void updateTourLogSuccessful(TourLog oldTourLog, TourLog updatedTour) {
        int oldIndex = baseList.indexOf(oldTourLog);
        baseList.remove(oldIndex);
        if (updatedTour.getDateTime() == null) {
            baseList.add(oldIndex, updatedTour);
        } else {
            baseList.add(updatedTour);
        }
        tableSelectionModel.select(updatedTour);
    }

    public void deleteSelectedTourLog() {
        this.deleteTourLogListener.deleteTour(this.selectedTourLog);
    }

    public void deleteTourLogSuccessful() {
        this.baseList.remove(this.selectedTourLog);
    }

}
