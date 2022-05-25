package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.TourLogTableCell;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomDoubleStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomIntegerStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalDateTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

public class TourLogData implements ViewModel {

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
    private TourLogDTO selectedTourLog;

    public TourLogData() {
        baseList = FXCollections.observableArrayList(List.of(new TourLogDTO(LocalDateTime.now(), "Aha", 1, LocalTime.MIDNIGHT, 12)));

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

    public void selectionChanged(ObservableValue<? extends TourLogDTO> observableValue, TourLogDTO oldValue, TourLogDTO newValue) {
        selectedTourLog = newValue;
    }
}
