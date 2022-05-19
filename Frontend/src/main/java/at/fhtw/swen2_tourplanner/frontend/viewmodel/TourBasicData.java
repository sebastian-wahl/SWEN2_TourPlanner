package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.Observer;
import at.fhtw.swen2_tourplanner.frontend.observer.UpdateTourObservable;
import at.fhtw.swen2_tourplanner.frontend.service.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourBasicData implements ViewModel, UpdateTourObservable {
    private static final String BUTTON_EDIT_TEXT = "Edit";
    private static final String BUTTON_SAVE_TEXT = "Save";
    // Services
    private final TourService tourService;
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
    private final StringProperty descriptionProperty;
    @Getter
    private final BooleanProperty descriptionDisableProperty;
    @Getter
    private final BooleanProperty checkboxDisableProperty;
    @Getter
    private final StringProperty editSaveButtonTextProperty;
    @Getter
    private final BooleanProperty editSaveButtonDisableProperty;
    @Getter
    private final BooleanProperty exportButtonDisableProperty;
    // observer list
    private List<Observer<TourDTO>> updateTourObserverList;
    private TourDTO currentTour;

    public TourBasicData(TourService tourService) {
        this.tourService = tourService;
        this.updateTourObserverList = new ArrayList<>();

        nameProperty = new SimpleStringProperty();
        nameDisableProperty = new SimpleBooleanProperty(true);
        fromProperty = new SimpleStringProperty();
        fromDisableProperty = new SimpleBooleanProperty(true);
        toProperty = new SimpleStringProperty();
        toDisableProperty = new SimpleBooleanProperty(true);
        distanceProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
        descriptionDisableProperty = new SimpleBooleanProperty(true);
        checkboxDisableProperty = new SimpleBooleanProperty(true);
        editSaveButtonTextProperty = new SimpleStringProperty(BUTTON_EDIT_TEXT);
        editSaveButtonDisableProperty = new SimpleBooleanProperty(false);
        exportButtonDisableProperty = new SimpleBooleanProperty(false);
    }

    public void setCurrentTour(TourDTO tour) {
        if (tour != null) {
            this.currentTour = tour;
            this.nameProperty.setValue(currentTour.getName());
            this.fromProperty.setValue(currentTour.getStart());
            this.toProperty.setValue(currentTour.getGoal());
            this.distanceProperty.setValue("" + currentTour.getTourDistance());
            this.descriptionProperty.setValue(currentTour.getTourDescription());
            this.enableEditSaveAndExportButtons();
        } else {
            this.nameProperty.setValue("");
            this.fromProperty.setValue("");
            this.toProperty.setValue("");
            this.distanceProperty.setValue("");
            this.descriptionProperty.setValue("");
            this.disableEditSaveAndExportButtons();
        }
        this.disableAllProperties();
    }

    private void enableEditSaveAndExportButtons() {
        this.editSaveButtonDisableProperty.setValue(false);
        this.exportButtonDisableProperty.setValue(false);
    }

    private void disableEditSaveAndExportButtons() {
        this.editSaveButtonDisableProperty.setValue(true);
        this.exportButtonDisableProperty.setValue(true);
    }

    private void setCurrentTourValues() {
        this.currentTour.setName(this.nameProperty.getValue());
        this.currentTour.setStart(this.fromProperty.getValue());
        this.currentTour.setGoal(this.toProperty.getValue());
        this.currentTour.setTourDescription(this.descriptionProperty.getValue());
    }

    public void editOrSaveTour() {
        if (currentTour != null) {
            if (editSaveButtonTextProperty.getValue().equals(BUTTON_SAVE_TEXT)) {
                // save
                this.setCurrentTourValues();
                Optional<TourDTO> updatedTour = this.tourService.updateTour(this.currentTour);
                if (updatedTour.isPresent()) {
                    this.setCurrentTour(updatedTour.get());
                    this.notifyObservers();
                    this.editSaveButtonTextProperty.setValue(BUTTON_EDIT_TEXT);
                }
            } else {
                // edit
                this.editSaveButtonTextProperty.setValue(BUTTON_SAVE_TEXT);
                this.enableAllProperties();
            }
        }
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
    public void registerObserver(Observer<TourDTO> observer) {
        this.updateTourObserverList.add(observer);
    }

    @Override
    public void removeObserver(Observer<TourDTO> observer) {
        this.updateTourObserverList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<TourDTO> observer : this.updateTourObserverList) {
            observer.update(this.currentTour, this.getClass());
        }
    }
}
