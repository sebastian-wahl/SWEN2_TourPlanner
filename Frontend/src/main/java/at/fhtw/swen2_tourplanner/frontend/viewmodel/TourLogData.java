package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourLogTableCell;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.*;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchBaseObserver;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.AddUpdateSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.DeleteSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.GetMultipleLogService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
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
import java.util.Optional;
import java.util.function.Predicate;

public class TourLogData implements ViewModel, SearchBaseObserver {
    // other view models
    private final Searchbar tourLogSearchbar;

    // services
    private final TourLogService tourLogService;

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
    private final ObjectProperty<Callback<TableColumn<TourLog, Long>, TableCell<TourLog, Long>>> distanceColCellFactoryProperty;

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

    private Tour currentTour;
    private TourLog selectedTourLog;

    @Setter
    private TableView.TableViewSelectionModel<TourLog> tableSelectionModel;

    public TourLogData(Searchbar tourLogSearchbar, TourLogService tourLogService) {
        this.tourLogSearchbar = tourLogSearchbar;
        this.tourLogService = tourLogService;

        // default disable tourlog searchbar
        this.tourLogSearchbar.disableSearchbar();

        baseList = FXCollections.observableArrayList();
        SortedList<TourLog> sortedList = setUpSortedList();

        tourLogList = new FilteredList<>(sortedList);
        // default filtering -> no filters set
        tourLogList.setPredicate(null);


        // Properties
        dateColCellFactoryProperty = new SimpleObjectProperty<>();
        dateColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalDateTimeStringConverter(), CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT.toUpperCase()));


        timeColCellFactoryProperty = new SimpleObjectProperty<>();
        timeColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalTimeStringConverter(), CustomLocalTimeStringConverter.TIME_FORMAT.toUpperCase()));

        difficultyColCellFactoryProperty = new SimpleObjectProperty<>();
        difficultyColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomIntegerStringConverter()));

        ratingColCellFactoryProperty = new SimpleObjectProperty<>();
        ratingColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomDoubleStringConverter()));

        distanceColCellFactoryProperty = new SimpleObjectProperty<>();
        distanceColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLongStringConverter()));

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
        TablePosition<TourLog, LocalDateTime> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setDateTime(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitTime(TableColumn.CellEditEvent<TourLog, LocalTime> event) {
        TablePosition<TourLog, LocalTime> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setTotalTime(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitDifficulty(TableColumn.CellEditEvent<TourLog, Integer> event) {
        TablePosition<TourLog, Integer> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setDifficulty(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitRating(TableColumn.CellEditEvent<TourLog, Double> event) {
        TablePosition<TourLog, Double> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setRating(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitDistance(TableColumn.CellEditEvent<TourLog, Long> event) {
        TablePosition<TourLog, Long> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setDistance(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
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
            this.setBaseTourLogList();
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

    private void setBaseTourLogList() {
        GetMultipleLogService getMultipleLogService = new GetMultipleLogService(tourLogService::getAllLogs, currentTour.getId());
        getMultipleLogService.valueProperty().addListener(new ChangeListener<List<TourLog>>() {
            @Override
            public void changed(ObservableValue<? extends List<TourLog>> observableValue, List<TourLog> tourDTOS, List<TourLog> newValues) {
                baseList.clear();
                baseList.addAll(newValues);
            }
        });
        getMultipleLogService.start();
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
                selectedTourLog.getDateTime().format(DateTimeFormatter.ofPattern(CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT)) : "00.00.0000 00:00");
        timeLabelProperty.setValue(selectedTourLog.getTotalTime() != null ?
                selectedTourLog.getTotalTime().format(DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT)) : "00:00:00");
        distanceLabelProperty.setValue("" + selectedTourLog.getDistance());
        difficultyLabelProperty.setValue(selectedTourLog.getDifficulty() + "");
        ratingLabelProperty.setValue(selectedTourLog.getRating() + "");
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
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT);
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT);
            Predicate<TourLog> startDatePredicate = tourLog -> tourLog.getDateTime() != null && formatterDate.format(tourLog.getDateTime()).contains(s);

            Predicate<TourLog> durationPredicate = tourLog -> tourLog.getTotalTime() != null && formatterTime.format(tourLog.getTotalTime()).contains(s);

            Predicate<TourLog> distancePredicate = tourLog -> ("" + tourLog.getDistance()).contains(s);

            Predicate<TourLog> difficultyPredicate = tourLog -> ("" + tourLog.getDifficulty()).contains(s);

            Predicate<TourLog> ratingPredicate = tourLog -> ("" + tourLog.getRating()).contains(s);

            Predicate<TourLog> commentPredicate = tourLog -> tourLog.getComment() != null && tourLog.getComment().contains(s);

            Predicate<TourLog> masterPredicate = startDatePredicate.or(durationPredicate)
                    .or(difficultyPredicate)
                    .or(distancePredicate)
                    .or(ratingPredicate)
                    .or(commentPredicate);
            tourLogList.setPredicate(masterPredicate);
        } else {
            tourLogList.setPredicate(null);
        }

    }

    /* ----------------------------------------- API request methods ------------------------------------------------- */

    public void addTourLog() {
        TourLog toAdd = new TourLog(currentTour);
        toAdd.setDistance(currentTour.getTourDistance());
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(tourLogService::addTourLog, new TourLog(currentTour));
        addUpdateSingleLogService.valueProperty().addListener(new ChangeListener<Optional<TourLog>>() {
            @Override
            public void changed(ObservableValue<? extends Optional<TourLog>> observableValue, Optional<TourLog> tourLog, Optional<TourLog> newValue) {
                newValue.ifPresent(newTourLog -> {
                    baseList.add(newTourLog);
                    tableSelectionModel.select(newTourLog);
                });
            }
        });
        addUpdateSingleLogService.start();
    }

    public void updateTourLog(TourLog toUpdate) {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(tourLogService::updateTourLog, toUpdate);
        addUpdateSingleLogService.valueProperty().addListener(new ChangeListener<Optional<TourLog>>() {
            @Override
            public void changed(ObservableValue<? extends Optional<TourLog>> observableValue, Optional<TourLog> tourLog, Optional<TourLog> newValue) {
                newValue.ifPresent(updatedTourLog -> {

                    int oldIndex = baseList.indexOf(toUpdate);
                    baseList.remove(toUpdate);
                    if (updatedTourLog.getDateTime() == null) {
                        baseList.add(oldIndex, updatedTourLog);
                    } else {
                        baseList.add(updatedTourLog);
                    }
                    tableSelectionModel.select(updatedTourLog);
                });
            }
        });
        addUpdateSingleLogService.start();
    }

    public void deleteSelectedTourLog() {
        DeleteSingleLogService deleteSingleLogService = new DeleteSingleLogService(tourLogService::deleteTourLog, this.selectedTourLog);
        deleteSingleLogService.valueProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean success) {
                if (success) {
                    baseList.remove(selectedTourLog);
                }
            }
        });
        deleteSingleLogService.start();
    }
}
