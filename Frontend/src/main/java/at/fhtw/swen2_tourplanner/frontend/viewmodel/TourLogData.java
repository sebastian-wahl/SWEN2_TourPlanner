package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourLogTableCell;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomDoubleStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomIntegerStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalDateTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.AddUpdateSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.DeleteSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.GetMultipleLogService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TourLogData implements ViewModel {
    // services
    private final TourLogService tourLogService;

    @Getter
    private final FilteredList<TourLogDTO> tourLogList;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLogDTO, LocalDateTime>, TableCell<TourLogDTO, LocalDateTime>>> dateColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLogDTO, LocalTime>, TableCell<TourLogDTO, LocalTime>>> timeColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLogDTO, Integer>, TableCell<TourLogDTO, Integer>>> difficultyColCellFactoryProperty;
    @Getter
    private final ObjectProperty<Callback<TableColumn<TourLogDTO, Double>, TableCell<TourLogDTO, Double>>> ratingColCellFactoryProperty;

    // label properties
    @Getter
    private final ObjectProperty<String> dateLabelProperty;

    @Getter
    private final ObjectProperty<String> timeLabelProperty;

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
    private final ObservableList<TourLogDTO> baseList;

    private TourDTO currentTour;
    private TourLogDTO selectedTourLog;

    @Setter
    private TableView.TableViewSelectionModel<TourLogDTO> tableSelectionModel;

    public TourLogData(TourLogService tourLogService) {
        this.tourLogService = tourLogService;

        baseList = FXCollections.observableArrayList();
        SortedList<TourLogDTO> sortedList = setUpSortedList();
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

        commentInputDisableProperty = new SimpleBooleanProperty(true);
        addLogButtonDisableProperty = new SimpleBooleanProperty(true);
        deleteLogButtonDisableProperty = new SimpleBooleanProperty(true);
        tableDisableProperty = new SimpleBooleanProperty(true);

        // labels
        dateLabelProperty = new SimpleObjectProperty<>();
        timeLabelProperty = new SimpleObjectProperty<>();
        difficultyLabelProperty = new SimpleObjectProperty<>();
        ratingLabelProperty = new SimpleObjectProperty<>();

        commentTextProperty = new SimpleStringProperty();
    }

    private SortedList<TourLogDTO> setUpSortedList() {
        return new SortedList<>(baseList,
                (TourLogDTO log1, TourLogDTO log2) -> {
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
                });
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
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            //this.selectedTourLog.setComment();
            this.selectedTourLog.setComment(commentTextProperty.getValue());
            this.updateTourLog(this.selectedTourLog);
            this.commentInputDisableProperty.setValue(true);
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            commentTextProperty.setValue("");
            this.commentInputDisableProperty.setValue(true);
        }

    }

    public void onEditCommitDate(TableColumn.CellEditEvent<TourLogDTO, LocalDateTime> event) {
        TablePosition<TourLogDTO, LocalDateTime> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setDateTime(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitTime(TableColumn.CellEditEvent<TourLogDTO, LocalTime> event) {
        TablePosition<TourLogDTO, LocalTime> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setTotalTime(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitDifficulty(TableColumn.CellEditEvent<TourLogDTO, Integer> event) {
        TablePosition<TourLogDTO, Integer> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setDifficulty(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void onEditCommitRating(TableColumn.CellEditEvent<TourLogDTO, Double> event) {
        TablePosition<TourLogDTO, Double> position = event.getTablePosition();
        int row = position.getRow();
        // update log element & update tour
        this.baseList.get(row).setRating(event.getNewValue());
        this.updateTourLog(this.baseList.get(row));
    }

    public void updateFromTourList(TourDTO tourDTO) {
        resetTourLogValueLabels();
        setCurrentTour(tourDTO);
    }

    private void setCurrentTour(TourDTO tour) {
        this.currentTour = tour;
        if (this.currentTour != null) {
            this.activateFields();
            this.setBaseTourLogList();
        } else {
            this.deactivateFields();
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
        getMultipleLogService.valueProperty().addListener(new ChangeListener<List<TourLogDTO>>() {
            @Override
            public void changed(ObservableValue<? extends List<TourLogDTO>> observableValue, List<TourLogDTO> tourDTOS, List<TourLogDTO> newValues) {
                baseList.clear();
                baseList.addAll(newValues);
            }
        });
        getMultipleLogService.start();
    }

    public void selectionChanged(ObservableValue<? extends TourLogDTO> observableValue, TourLogDTO oldValue, TourLogDTO newValue) {
        selectedTourLog = newValue;
        if (selectedTourLog != null) {
            setTourLogValuesIntoLabels();
        } else {
            resetTourLogValueLabels();
        }
    }

    private void setTourLogValuesIntoLabels() {
        dateLabelProperty.setValue(selectedTourLog.getDateTime() != null ?
                selectedTourLog.getDateTime().format(DateTimeFormatter.ofPattern(CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT)) : "");
        timeLabelProperty.setValue(selectedTourLog.getTotalTime() != null ?
                selectedTourLog.getTotalTime().format(DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT)) : "");
        difficultyLabelProperty.setValue(Objects.requireNonNullElse(selectedTourLog.getDifficulty(), "") + "");
        ratingLabelProperty.setValue(Objects.requireNonNullElse(selectedTourLog.getRating(), "") + "");
        commentTextProperty.setValue(Objects.requireNonNullElse(selectedTourLog.getComment(), ""));
    }

    private void resetTourLogValueLabels() {
        dateLabelProperty.setValue("");
        timeLabelProperty.setValue("");
        difficultyLabelProperty.setValue("");
        ratingLabelProperty.setValue("");
        commentTextProperty.setValue("");
    }

    /* ----------------------------------------- API request methods ------------------------------------------------- */

    public void addTourLog() {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(tourLogService::addTourLog, new TourLogDTO(currentTour));
        addUpdateSingleLogService.valueProperty().addListener(new ChangeListener<Optional<TourLogDTO>>() {
            @Override
            public void changed(ObservableValue<? extends Optional<TourLogDTO>> observableValue, Optional<TourLogDTO> tourLogDTO, Optional<TourLogDTO> newValue) {
                newValue.ifPresent(newTourLog -> {
                    baseList.add(newTourLog);
                    tableSelectionModel.select(newTourLog);
                });
            }
        });
        addUpdateSingleLogService.start();
    }

    public void updateTourLog(TourLogDTO toUpdate) {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(tourLogService::updateTourLog, toUpdate);
        addUpdateSingleLogService.valueProperty().addListener(new ChangeListener<Optional<TourLogDTO>>() {
            @Override
            public void changed(ObservableValue<? extends Optional<TourLogDTO>> observableValue, Optional<TourLogDTO> tourLogDTO, Optional<TourLogDTO> newValue) {
                newValue.ifPresent(updatedTourLog -> {
                    baseList.remove(toUpdate);
                    baseList.add(updatedTourLog);
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
