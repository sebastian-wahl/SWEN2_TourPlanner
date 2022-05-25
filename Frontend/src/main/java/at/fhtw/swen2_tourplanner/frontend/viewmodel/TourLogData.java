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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    private ObservableList<TourLogDTO> baseList;

    private TourDTO currentTour;
    private TourLogDTO selectedTourLog;

    public TourLogData(TourLogService tourLogService) {
        this.tourLogService = tourLogService;

        baseList = FXCollections.observableArrayList();
        if (currentTour != null) {
            this.setBaseTourLogList();
        }

        tourLogList = new FilteredList<>(baseList);
        // default filtering -> no filters set
        tourLogList.setPredicate(null);

        // Properties
        dateColCellFactoryProperty = new SimpleObjectProperty<>();
        dateColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalDateTimeStringConverter()));

        timeColCellFactoryProperty = new SimpleObjectProperty<>();
        timeColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomLocalTimeStringConverter()));

        difficultyColCellFactoryProperty = new SimpleObjectProperty<>();
        difficultyColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomIntegerStringConverter()));

        ratingColCellFactoryProperty = new SimpleObjectProperty<>();
        ratingColCellFactoryProperty.setValue(col -> new TourLogTableCell<>(new CustomDoubleStringConverter()));
    }

    public void updateFromTourList(TourDTO tourDTO) {
        setCurrentTour(tourDTO);
    }
    private void setCurrentTour(TourDTO tour) {
        this.currentTour = tour;
        this.setBaseTourLogList();
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
    }

    public void addTourLog() {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(tourLogService::addTour, new TourLogDTO(currentTour));
        addUpdateSingleLogService.valueProperty().addListener(new ChangeListener<Optional<TourLogDTO>>() {
            @Override
            public void changed(ObservableValue<? extends Optional<TourLogDTO>> observableValue, Optional<TourLogDTO> tourLogDTO, Optional<TourLogDTO> newValue) {
                newValue.ifPresent(logDTO -> baseList.add(logDTO));
            }
        });
        addUpdateSingleLogService.start();
    }

    public void deleteSelectedTourLog() {
        DeleteSingleLogService deleteSingleLogService = new DeleteSingleLogService(tourLogService::deleteTour, this.selectedTourLog);
        // Maybe check result?
        deleteSingleLogService.start();
    }

    public void updateTourLog() {
    }
}
