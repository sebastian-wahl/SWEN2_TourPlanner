package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.enums.TransportTypeEnum;
import at.fhtw.swen2_tourplanner.frontend.listener.UpdateListener;
import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObservable;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourBasicData implements ViewModel, UpdateTourObservable {

    private static final String BUTTON_EDIT_TEXT = "Edit";
    private static final String BUTTON_SAVE_TEXT = "Save";
    // logger
    private final Logger logger = LogManager.getLogger(TourBasicData.class);
    // Services
    // Properties
    @Getter
    private final StringProperty nameProperty;
    @Getter
    private final BooleanProperty nameDisableProperty;
    @Getter
    private final StringProperty fromProperty;
    @Getter
    private final BooleanProperty fromDisableProperty;
    @Getter
    private final StringProperty toProperty;
    @Getter
    private final BooleanProperty toDisableProperty;
    @Getter
    private final StringProperty distanceProperty;
    @Getter
    private final StringProperty estimatedTimeProperty;
    @Getter
    private final StringProperty descriptionProperty;
    @Getter
    private final BooleanProperty descriptionDisableProperty;
    @Getter
    private final BooleanProperty favoriteCheckboxProperty;
    @Getter
    private final BooleanProperty checkboxDisableProperty;
    @Getter
    private final StringProperty editSaveButtonTextProperty;
    @Getter
    private final BooleanProperty editSaveButtonDisableProperty;
    @Getter
    private final BooleanProperty exportButtonDisableProperty;

    @Getter
    private final BooleanProperty exportSummaryButtonDisableProperty;

    @Getter
    private final BooleanProperty transportTypeDisableProperty;
    @Getter
    private final ObjectProperty<ObservableList<String>> transportTypeItemsProperty;
    @Getter
    private final ObjectProperty<String> transportTypeSelectedItemProperty;
    // observer list
    private final List<BaseObserver<Tour>> updateTourBaseObserverList;

    // single Listeners
    @Setter
    private UpdateListener<Tour> tourUpdateListener;
    private Tour currentTour;

    public TourBasicData() {
        this.updateTourBaseObserverList = new ArrayList<>();

        nameProperty = new SimpleStringProperty();
        nameDisableProperty = new SimpleBooleanProperty(true);
        fromProperty = new SimpleStringProperty();
        fromDisableProperty = new SimpleBooleanProperty(true);
        toProperty = new SimpleStringProperty();
        toDisableProperty = new SimpleBooleanProperty(true);
        distanceProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        estimatedTimeProperty = new SimpleStringProperty();
        descriptionDisableProperty = new SimpleBooleanProperty(true);
        favoriteCheckboxProperty = new SimpleBooleanProperty(false);
        checkboxDisableProperty = new SimpleBooleanProperty(true);
        editSaveButtonTextProperty = new SimpleStringProperty(BUTTON_EDIT_TEXT);
        editSaveButtonDisableProperty = new SimpleBooleanProperty(true);
        exportButtonDisableProperty = new SimpleBooleanProperty(true);
        exportSummaryButtonDisableProperty = new SimpleBooleanProperty(true);

        transportTypeDisableProperty = new SimpleBooleanProperty(true);
        transportTypeItemsProperty = new SimpleObjectProperty<>(
                FXCollections.observableArrayList(
                        Arrays.stream(TransportTypeEnum.values())
                                .map(TransportTypeEnum::getName).toList()
                )
        );
        transportTypeSelectedItemProperty = new SimpleObjectProperty<>();
    }

    public void setCurrentTour(Tour tour) {
        if (tour != null) {
            this.currentTour = tour;
            this.nameProperty.setValue(currentTour.getName());
            this.fromProperty.setValue(currentTour.getStart());
            this.toProperty.setValue(currentTour.getGoal());
            this.distanceProperty.setValue("" + currentTour.getTourDistance() + " km");
            this.descriptionProperty.setValue(currentTour.getTourDescription());
            this.estimatedTimeProperty.setValue(getTimeString() + " (HH:MM:SS)");
            this.favoriteCheckboxProperty.setValue(currentTour.isFavorite());
            this.transportTypeSelectedItemProperty.setValue(TransportTypeEnum.valueOf(tour.getTransportType()).getName());
            this.enableEditSaveAndExportButtons();
        } else {
            this.nameProperty.setValue("");
            this.fromProperty.setValue("");
            this.toProperty.setValue("");
            this.distanceProperty.setValue("");
            this.descriptionProperty.setValue("");
            this.estimatedTimeProperty.setValue("");
            this.favoriteCheckboxProperty.setValue(false);
            this.transportTypeSelectedItemProperty.setValue("");
            this.disableEditSaveAndExportButtons();
        }
        this.disableAllProperties();
        this.editSaveButtonTextProperty.setValue(BUTTON_EDIT_TEXT);
    }

    private String getTimeString() {
        return currentTour.getEstimatedTime() != null ?
                currentTour.getEstimatedTime().format(DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT))
                :
                "00:00:00";
    }

    private void enableEditSaveAndExportButtons() {
        this.changeEditSaveAndExportButtons(false);
    }

    private void disableEditSaveAndExportButtons() {
        this.changeEditSaveAndExportButtons(true);
    }

    private void changeEditSaveAndExportButtons(boolean disable) {
        this.editSaveButtonDisableProperty.setValue(disable);
        this.exportButtonDisableProperty.setValue(disable);
        this.exportSummaryButtonDisableProperty.setValue(disable);
    }

    private void setCurrentTourValues() {
        this.currentTour.setName(this.nameProperty.getValue());
        this.currentTour.setStart(this.fromProperty.getValue());
        this.currentTour.setGoal(this.toProperty.getValue());
        this.currentTour.setTourDescription(this.descriptionProperty.getValue());
        this.currentTour.setFavorite(this.favoriteCheckboxProperty.getValue());
    }

    public void editOrSaveTour() {
        if (currentTour != null) {
            if (editSaveButtonTextProperty.getValue().equals(BUTTON_SAVE_TEXT)) {
                // save
                saveCurrentTour();
            } else {
                // edit
                this.editSaveButtonTextProperty.setValue(BUTTON_SAVE_TEXT);
                this.enableAllProperties();
            }
        }
    }

    private void saveCurrentTour() {
        this.setCurrentTourValues();
        this.tourUpdateListener.updateTour(currentTour);
        this.disableAllProperties();
        this.editSaveButtonDisableProperty.setValue(true);
    }

    public void updateTourSuccessful(Tour newValue) {
        this.editSaveButtonDisableProperty.setValue(false);
        setCurrentTour(newValue);
        // update map
        notifyObservers();
        editSaveButtonTextProperty.setValue(BUTTON_EDIT_TEXT);
    }

    private void enableAllProperties() {
        this.changeDisableProperties(false);
    }

    private void disableAllProperties() {
        this.changeDisableProperties(true);
    }

    private void changeDisableProperties(boolean to) {
        this.nameDisableProperty.setValue(to);
        this.fromDisableProperty.setValue(to);
        this.toDisableProperty.setValue(to);
        this.descriptionDisableProperty.setValue(to);
        this.checkboxDisableProperty.setValue(to);
    }

    @Override
    public void registerObserver(BaseObserver<Tour> baseObserver) {
        this.updateTourBaseObserverList.add(baseObserver);
    }

    @Override
    public void removeObserver(BaseObserver<Tour> baseObserver) {
        this.updateTourBaseObserverList.remove(baseObserver);
    }

    @Override
    public void notifyObservers() {
        for (BaseObserver<Tour> baseObserver : this.updateTourBaseObserverList) {
            baseObserver.update(this.currentTour);
        }
    }
}
