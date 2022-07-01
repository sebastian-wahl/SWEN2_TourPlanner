package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.enums.TransportTypeEnum;
import at.fhtw.swen2_tourplanner.frontend.listener.ExportTourListener;
import at.fhtw.swen2_tourplanner.frontend.listener.ExportTourReportListener;
import at.fhtw.swen2_tourplanner.frontend.listener.UpdateListener;
import at.fhtw.swen2_tourplanner.frontend.listener.ValidationListener;
import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObservable;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class TourBasicData implements ViewModel, UpdateTourObservable {

    private static final String BUTTON_EDIT_TEXT = "Edit";
    private static final String BUTTON_SAVE_TEXT = "Save";
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
    private final StringProperty childFriendlinessProperty;
    @Getter
    private final StringProperty popularityProperty;
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
    @Getter
    private final BooleanProperty validateToButtonDisableProperty;
    @Getter
    private final BooleanProperty validateFromButtonDisableProperty;
    @Getter
    private final BooleanProperty validateFromButtonVisibilityProperty;
    @Getter
    private final BooleanProperty validateToButtonVisibilityProperty;

    // observer list
    private final List<BaseObserver<Tour>> updateTourBaseObserverList;
    private final DecimalFormat df;
    // single Listeners
    @Setter
    private ExportTourListener exportTourListener;
    @Setter
    private ExportTourReportListener exportTourReportListener;
    @Setter
    private UpdateListener<Tour> tourUpdateListener;
    @Setter
    private ValidationListener validationListenerFrom;
    @Setter
    private ValidationListener validationListenerTo;
    private Tour currentTour;

    public TourBasicData() {
        df = new DecimalFormat("##." + "0".repeat(2));
        this.updateTourBaseObserverList = new ArrayList<>();

        nameProperty = new SimpleStringProperty();
        nameDisableProperty = new SimpleBooleanProperty(true);
        fromProperty = new SimpleStringProperty();
        fromProperty.addListener(this::fromLocationChanged);
        fromDisableProperty = new SimpleBooleanProperty(true);
        toProperty = new SimpleStringProperty();
        toProperty.addListener(this::toLocationChanged);
        toDisableProperty = new SimpleBooleanProperty(true);
        distanceProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        estimatedTimeProperty = new SimpleStringProperty();
        childFriendlinessProperty = new SimpleStringProperty();
        popularityProperty = new SimpleStringProperty();
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

        validateToButtonDisableProperty = new SimpleBooleanProperty(false);
        validateFromButtonDisableProperty = new SimpleBooleanProperty(false);
        validateFromButtonVisibilityProperty = new SimpleBooleanProperty(false);
        validateToButtonVisibilityProperty = new SimpleBooleanProperty(false);
    }

    private void toLocationChanged(Observable observable) {
        this.validateToButtonDisableProperty.setValue(false);
        editSaveButtonDisableProperty.setValue(true);
    }

    private void fromLocationChanged(Observable observable) {
        this.validateFromButtonDisableProperty.setValue(false);
        editSaveButtonDisableProperty.setValue(true);
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
            this.popularityProperty.setValue(currentTour.getPopularity() + "/3");
            this.childFriendlinessProperty.setValue(df.format(currentTour.getChildFriendliness()).replace(",", ".") + "/3.00");
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
            this.childFriendlinessProperty.setValue("");
            this.popularityProperty.setValue("");
            this.favoriteCheckboxProperty.setValue(false);
            this.transportTypeSelectedItemProperty.setValue("");
            this.disableEditSaveAndExportButtons();
        }
        this.hideValidateButtons();
        this.disableAllProperties();
        this.editSaveButtonTextProperty.setValue(BUTTON_EDIT_TEXT);
    }

    private void hideValidateButtons() {
        this.validateFromButtonVisibilityProperty.setValue(false);
        this.validateToButtonVisibilityProperty.setValue(false);
    }

    private void showValidateButtons() {
        this.validateFromButtonDisableProperty.setValue(false);
        this.validateToButtonDisableProperty.setValue(false);
        this.validateFromButtonVisibilityProperty.setValue(true);
        this.validateToButtonVisibilityProperty.setValue(true);
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
        this.currentTour.setTransportType(TransportTypeEnum.valueOf(this.transportTypeSelectedItemProperty.get().toUpperCase()).getDbValue());
    }

    public void editOrSaveTour() {
        if (currentTour != null) {
            if (editSaveButtonTextProperty.getValue().equals(BUTTON_SAVE_TEXT)) {
                // save
                saveCurrentTour();
                this.hideValidateButtons();
            } else {
                // edit
                this.editSaveButtonTextProperty.setValue(BUTTON_SAVE_TEXT);
                this.editSaveButtonDisableProperty.setValue(true);
                this.enableAllProperties();
                this.showValidateButtons();
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

    public void computedAttributesSuccessful(Tour tourOnlyAttributes) {
        this.currentTour.setChildFriendliness(tourOnlyAttributes.getChildFriendliness());
        this.currentTour.setPopularity(tourOnlyAttributes.getPopularity());
        this.setCurrentTour(this.currentTour);
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
        this.transportTypeDisableProperty.setValue(to);
    }

    public void exportTour(File saveToFile) {
        this.exportTourListener.exportTour(saveToFile, this.currentTour);
    }

    public void exportTourReport(File saveToFile) {
        this.exportTourReportListener.exportTourReport(saveToFile, this.currentTour);
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

    public void validateToInput() {
        this.validationListenerTo.validateLocation(this.toProperty.getValue());
    }

    public void inputToValidationSuccessful() {
        this.validateToButtonDisableProperty.setValue(true);
        if (validateFromButtonDisableProperty.get()) {
            this.editSaveButtonDisableProperty.setValue(false);
        }
    }

    public void inputToValidationFailed() {
        this.validateToButtonDisableProperty.setValue(false);
        this.toProperty.setValue("");
    }

    public void validateFromInput() {
        this.validationListenerFrom.validateLocation(this.fromProperty.getValue());
    }

    public void inputFromValidationSuccessful() {
        this.validateFromButtonDisableProperty.setValue(true);
        if (validateToButtonDisableProperty.get()) {
            this.editSaveButtonDisableProperty.setValue(false);
        }
    }

    public void inputFromValidationFailed() {
        this.validateFromButtonDisableProperty.setValue(false);
        this.fromProperty.setValue("");
    }
}
