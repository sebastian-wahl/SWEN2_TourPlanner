package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.StringBooleanObserver;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.AddUpdateSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.DeleteSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.GetMultipleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.AddUpdateSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.DeleteSingleLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.GetMultipleLogService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.beans.value.ObservableValue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Dashboard implements ViewModel, StringBooleanObserver {

    // Models
    private final InfoLine infoLine;
    private final TourList tourList;
    private final TourBasicData tourBasicData;
    private final TourMap tourMap;
    private final TourLogData tourLogData;
    // Services
    private final TourService tourService;
    private final TourLogService tourLogService;

    public Dashboard(TourList tourList, TourBasicData tourBasicData, TourMap tourMap, TourLogData tourLogData, InfoLine infoLine, TourService tourService, TourLogService tourLogService) {
        this.tourList = tourList;
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;
        this.tourLogData = tourLogData;
        this.tourService = tourService;
        this.tourLogService = tourLogService;
        this.infoLine = infoLine;

        // listeners
        // set listeners for tour api calls
        this.tourBasicData.setTourUpdateListener(this::updateTourData);
        this.tourList.setTourDeleteListener(this::deleteTour);
        this.tourList.setTourAddListener(this::createTour);
        this.tourList.setTourGetListener(this::getAllTours);
        // set listeners for tour log api calls
        this.tourLogData.setGetTourLogListener(this::getAllTourLogs);
        this.tourLogData.setAddTourLogListener(this::addTourLog);
        this.tourLogData.setUpdateTourLogListener(this::updateTourLog);
        this.tourLogData.setDeleteTourLogListener(this::deleteTourLog);
    }

    /* --------------------- Tour API calls ---------------------------- */

    @Override
    public void update(String text, boolean showLoading) {
        this.infoLine.setInfoText(text);
        if (showLoading) this.infoLine.startLoading();
        else this.infoLine.stopLoading();
    }

    /**
     * From {@link TourList}
     */
    private void getAllTours() {
        GetMultipleTourService getMultipleTourService = new GetMultipleTourService(this::getAllToursCatchException);
        getMultipleTourService.valueProperty().addListener((ObservableValue<? extends List<Tour>> observableValue, List<Tour> tours, List<Tour> newTours) -> {
            if (!newTours.isEmpty()) {
                this.tourList.getTourSuccessful(newTours);
            }
            this.infoLine.stopLoading();
        });
        getMultipleTourService.start();
        this.infoLine.startLoading();
    }

    private List<Tour> getAllToursCatchException() {
        List<Tour> out;
        try {
            out = tourService.getAllTours();
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            out = Collections.emptyList();
            this.infoLine.setInfoText(e.getMessage());
        }
        return out;
    }

    /**
     * From {@link TourList}
     *
     * @param toCreate
     */
    private void createTour(Tour toCreate) {
        AddUpdateSingleTourService addUpdateSingleTourService = new AddUpdateSingleTourService(this::createTourCatchException, toCreate);
        addUpdateSingleTourService.valueProperty().addListener((observableValue, tourDTO, newValue) -> {
            if (newValue.isPresent()) {
                this.tourList.addTourSuccessful(newValue.get());
            } else {
                // error
            }
            this.infoLine.stopLoading();
        });
        addUpdateSingleTourService.start();
        this.infoLine.startLoading();
    }

    private Optional<Tour> createTourCatchException(Tour tour) {
        Optional<Tour> out;
        try {
            out = tourService.addTour(tour);
        } catch (BackendConnectionException | ApiCallTimoutException ex) {
            this.infoLine.setInfoText(ex.getMessage());
            out = Optional.empty();
        }
        return out;
    }

    /**
     * From {@link TourList}
     *
     * @param toDelete
     */
    private void deleteTour(Tour toDelete) {
        DeleteSingleTourService deleteSingleTourService = new DeleteSingleTourService(this::deleteTourCatchException, toDelete);
        deleteSingleTourService.valueProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean deleted) -> {
            if (Boolean.TRUE.equals(deleted)) {
                this.tourList.deleteTourSuccessful(toDelete);
            }
            this.infoLine.stopLoading();
        });
        deleteSingleTourService.start();
        this.infoLine.startLoading();
    }

    private boolean deleteTourCatchException(Tour toDelete) {
        try {
            return tourService.deleteTour(toDelete);
        } catch (BackendConnectionException | ApiCallTimoutException ex) {
            this.infoLine.setInfoText(ex.getMessage());
        }
        return false;
    }

    /**
     * From {@link TourBasicData}
     *
     * @param toUpdate
     */
    private void updateTourData(Tour toUpdate) {
        AddUpdateSingleTourService addUpdateSingleTourService = new AddUpdateSingleTourService(this::updateTourCatchException, toUpdate);
        addUpdateSingleTourService.valueProperty().addListener((observableValue, tourDTO, newValue) -> {
            if (newValue.isPresent()) {
                this.tourBasicData.updateTourSuccessful(newValue.get());
                this.tourList.updateTourSuccessful(newValue.get());
            } else {
                // error
            }
            this.infoLine.stopLoading();
        });
        addUpdateSingleTourService.start();
        this.infoLine.startLoading();
    }

    private Optional<Tour> updateTourCatchException(Tour tour) {
        Optional<Tour> out;
        try {
            out = tourService.updateTour(tour);
        } catch (BackendConnectionException | ApiCallTimoutException ex) {
            this.infoLine.setInfoText(ex.getMessage());
            out = Optional.empty();
        }
        return out;
    }

    /* --------------------- Tour log API calls ---------------------------- */

    /**
     * From {@link TourLogData}
     *
     * @param currentTourId
     */
    private void getAllTourLogs(UUID currentTourId) {
        GetMultipleLogService getMultipleLogService = new GetMultipleLogService(this::getAllTourLogsCatchException, currentTourId);
        getMultipleLogService.valueProperty().addListener((ObservableValue<? extends List<TourLog>> observableValue, List<TourLog> tourDTOS, List<TourLog> newValues) -> {
            this.tourLogData.getAllLogsSuccessful(newValues);
            this.infoLine.stopLoading();
        });
        getMultipleLogService.start();
        this.infoLine.startLoading();
    }

    private List<TourLog> getAllTourLogsCatchException(UUID uuid) {
        List<TourLog> out;
        try {
            out = this.tourLogService.getAllLogs(uuid);
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            out = Collections.emptyList();
            this.infoLine.setInfoText(e.getMessage());
        }
        return out;
    }

    /**
     * From {@link TourLogData}
     *
     * @param toAdd
     */
    private void addTourLog(TourLog toAdd) {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(this::addTourLogCatchException, toAdd);
        addUpdateSingleLogService.valueProperty().addListener((ObservableValue<? extends Optional<TourLog>> observableValue, Optional<TourLog> tourLog, Optional<TourLog> newValue) -> {
            newValue.ifPresent(this.tourLogData::addTourLogSuccessful);
            this.infoLine.stopLoading();
        });
        addUpdateSingleLogService.start();
        this.infoLine.startLoading();
    }

    private Optional<TourLog> addTourLogCatchException(TourLog toAdd) {
        try {
            return tourLogService.addTourLog(toAdd);
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.infoLine.setInfoText(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * From {@link TourLogData}
     *
     * @param toUpdate
     */
    private void updateTourLog(TourLog toUpdate) {
        AddUpdateSingleLogService addUpdateSingleLogService = new AddUpdateSingleLogService(this::updateTourCatchException, toUpdate);
        addUpdateSingleLogService.valueProperty().addListener((ObservableValue<? extends Optional<TourLog>> observableValue, Optional<TourLog> tourLog, Optional<TourLog> newValue) -> {
            newValue.ifPresent(updatedTourLog -> tourLogData.updateTourLogSuccessful(toUpdate, updatedTourLog));
            this.infoLine.stopLoading();
        });
        addUpdateSingleLogService.start();
        this.infoLine.startLoading();
    }

    private Optional<TourLog> updateTourCatchException(TourLog toUpdate) {
        try {
            return this.tourLogService.updateTourLog(toUpdate);
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.infoLine.setInfoText(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * From {@link TourLogData}
     *
     * @param toDelete
     */
    private void deleteTourLog(TourLog toDelete) {
        DeleteSingleLogService deleteSingleLogService = new DeleteSingleLogService(this::deleteTourLogCatchException, toDelete);
        deleteSingleLogService.valueProperty().addListener((observableValue, aBoolean, success) -> {
            if (Boolean.TRUE.equals(success)) {
                this.tourLogData.deleteTourLogSuccessful();
            }
            this.infoLine.stopLoading();
        });
        deleteSingleLogService.start();
        this.infoLine.startLoading();
    }

    private boolean deleteTourLogCatchException(TourLog toDelete) {
        try {
            return this.tourLogService.deleteTourLog(toDelete);
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.infoLine.setInfoText(e.getMessage());
            return false;
        }
    }
}
