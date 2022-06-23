package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.service.exceptions.ApiCallTimoutException;
import at.fhtw.swen2_tourplanner.frontend.service.exceptions.BackendConnectionException;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.AddMultipleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.AddUpdateSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.DeleteSingleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.microservice.GetMultipleTourService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice.*;
import at.fhtw.swen2_tourplanner.frontend.util.FileConverter;
import at.fhtw.swen2_tourplanner.frontend.util.exceptions.FileConvertException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Dashboard implements ViewModel {
    // Logger
    private final Logger logger = LogManager.getLogger(Dashboard.class);

    // Models
    private final InfoLine infoLine;
    private final TourList tourList;
    private final TourBasicData tourBasicData;
    private final TourMap tourMap;
    private final TourLogData tourLogData;

    private final Menubar menubar;
    // Services
    private final TourService tourService;
    private final TourLogService tourLogService;

    private final ObjectMapper o;

    public Dashboard(TourList tourList, TourBasicData tourBasicData, TourMap tourMap, TourLogData tourLogData,
                     InfoLine infoLine, TourService tourService, TourLogService tourLogService, Menubar menubar) {
        this.tourList = tourList;
        this.tourBasicData = tourBasicData;
        this.tourMap = tourMap;
        this.tourLogData = tourLogData;
        this.tourService = tourService;
        this.tourLogService = tourLogService;
        this.infoLine = infoLine;
        this.menubar = menubar;

        this.o = new ObjectMapper();
        o.registerModule(new JavaTimeModule());

        // listeners
        // set listeners for tour basic data export calls
        this.tourBasicData.setExportTourListener(this::exportTour);
        this.tourBasicData.setExportTourReportListener(this::exportTourReport);
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
        // set menubar listeners
        this.menubar.setImportTourListener(this::importTour);
        this.menubar.setExportTourSummaryListener(this::exportTourSummary);
    }

    /* --------------------- Tour export/import API calls --------------------- */

    private void importTour(List<File> files) {
        try {
            this.infoLine.startLoading();
            List<Tour> toAddWithId = FileConverter.convertFileToTour(files);
            Service<List<Tour>> addMultipleTourService = new AddMultipleTourService(this::importToursCatchException, toAddWithId);
            addMultipleTourService.valueProperty().addListener((ObservableValue<? extends List<Tour>> observableValue, List<Tour> tourList, List<Tour> t1) -> {
                if (!t1.isEmpty()) {
                    this.infoLine.setInfoText("Tour(s) imported successfully.");
                    this.tourList.importToursSuccessful(t1);
                } else {
                    this.infoLine.setErrorText("An error occurred while importing the Tour(s).");
                    this.logger.error("Error while importing tours.");
                }
                this.infoLine.stopLoading();
            });
            addMultipleTourService.start();
        } catch (FileConvertException e) {
            this.logger.error(e);
            this.infoLine.setErrorText(e.getMessage());
            this.infoLine.stopLoading();
        }
    }

    private List<Tour> importToursCatchException(List<Tour> tourList) {
        try {
            return this.tourService.importTours(tourList);
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.logger.error(e);
            this.infoLine.setErrorText(e.getMessage());
        }
        return Collections.emptyList();
    }

    private void exportTourSummary(File file) {
        if (this.createFile(file)) {
            this.infoLine.startLoading();
            Service<byte[]> getReportService = new GetSummaryService(this::getSummaryCatchException);
            getReportService.valueProperty().addListener((ObservableValue<? extends byte[]> observableValue, byte[] bytes, byte[] newValue) -> {
                if (newValue.length > 0) {
                    // success
                    try (OutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(this.o.writeValueAsBytes(newValue));
                        this.infoLine.setInfoText("Tour summary exported successfully!");
                    } catch (IOException e) {
                        this.infoLine.setErrorText("An error happened while exporting the tour summary. Please try again!");
                        this.logger.error("The Tour summary export went wrong. Error message: {}", e.getMessage());
                    }
                } else {
                    this.logger.error("The Tour summary export went wrong. Empty byte array returned.");
                    this.infoLine.setErrorText("An error happened while exporting the tour summary. Please try again!");
                }
                this.infoLine.stopLoading();
            });
            getReportService.start();
        }
    }

    private byte[] getSummaryCatchException() {
        try {
            byte[] summary = this.tourLogService.getTourSummary();
            if (summary == null) {
                return new byte[0];
            }
            return summary;
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.logger.error(e);
            this.infoLine.setErrorText(e.getMessage());
            return new byte[0];
        }
    }

    private void exportTourReport(File file, Tour toExport) {
        if (this.createFile(file)) {
            this.infoLine.startLoading();
            Service<byte[]> getReportService = new GetReportService(this::getReportCatchException, toExport.getId());
            getReportService.valueProperty().addListener((ObservableValue<? extends byte[]> observableValue, byte[] bytes, byte[] newValue) -> {
                if (newValue.length > 0) {
                    // success
                    try (OutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(newValue);
                        this.infoLine.setInfoText("Tour report exported successfully!");
                    } catch (IOException e) {
                        this.infoLine.setErrorText("An error happened while exporting the tour report. Please try again!");
                        this.logger.error("The Tour report export went wrong. Error message: {}", e.getMessage());
                    }
                } else {
                    this.logger.error("The Tour report export went wrong. Empty byte array returned.");
                    this.infoLine.setErrorText("An error happened while exporting the tour report. Please try again!");
                }
                this.infoLine.stopLoading();
            });
            getReportService.start();
        }
    }

    private byte[] getReportCatchException(UUID tourId) {
        try {
            byte[] report = this.tourLogService.getTourReport(tourId);
            if (report == null) {
                return new byte[0];
            }
            return report;
        } catch (BackendConnectionException | ApiCallTimoutException e) {
            this.logger.error(e);
            this.infoLine.setErrorText(e.getMessage());
            return new byte[0];
        }
    }


    private void exportTour(File file, Tour toExport) {
        if (this.createFile(file)) {
            this.infoLine.startLoading();
            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(this.o.writeValueAsBytes(toExport));
                this.infoLine.setInfoText("Tour exported successfully!");
            } catch (IOException e) {
                this.infoLine.setErrorText("An error happened while exporting the tour. Please try again!");
                this.logger.error("The Tour export went wrong. Error message: {}", e.getMessage());

            }
            this.infoLine.stopLoading();
        }
    }

    private boolean createFile(File file) {
        try {
            boolean fileCreated = file.createNewFile();
            this.logger.info("File {}", fileCreated ? "created" : "overwritten");
            return true;
        } catch (IOException e) {
            this.infoLine.setErrorText("Error while creating a file for the export.");
            this.logger.error("Error while creating a file for the export. {}", e.getMessage());
            return false;
        }
    }

    /* --------------------- Tour API calls ---------------------------- */

    /**
     * From {@link TourList}
     */
    private void getAllTours() {
        Service<List<Tour>> getMultipleTourService = new GetMultipleTourService(this::getAllToursCatchException);
        getMultipleTourService.valueProperty().addListener((ObservableValue<? extends List<Tour>> observableValue, List<Tour> tours, List<Tour> newTours) -> {
            if (!newTours.isEmpty()) {
                this.tourList.getTourSuccessful(newTours);
            } else {
                this.logger.warn("No tours fetched. Maybe something went wrong.");
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
            this.infoLine.setErrorText(e.getMessage());
        }
        return out;
    }

    /**
     * From {@link TourList}
     *
     * @param toCreate
     */
    private void createTour(Tour toCreate) {
        Service<Optional<Tour>> addUpdateSingleTourService = new AddUpdateSingleTourService(this::createTourCatchException, toCreate);
        addUpdateSingleTourService.valueProperty().addListener((observableValue, tourDTO, newValue) -> {
            if (newValue.isPresent()) {
                this.tourList.addTourSuccessful(newValue.get());
            } else {
                this.logger.warn("Tour \"{}\" not created successfully.", toCreate.getName());
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
            this.infoLine.setErrorText(ex.getMessage());
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
        Service<Boolean> deleteSingleTourService = new DeleteSingleTourService(this::deleteTourCatchException, toDelete);
        deleteSingleTourService.valueProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean deleted) -> {
            if (Boolean.TRUE.equals(deleted)) {
                this.tourList.deleteTourSuccessful(toDelete);
            } else {
                this.logger.warn("Tour not deleted successfully.");
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
            this.infoLine.setErrorText(ex.getMessage());
        }
        return false;
    }

    /**
     * From {@link TourBasicData}
     *
     * @param toUpdate
     */
    private void updateTourData(Tour toUpdate) {
        Service<Optional<Tour>> addUpdateSingleTourService = new AddUpdateSingleTourService(this::updateTourCatchException, toUpdate);
        addUpdateSingleTourService.valueProperty().addListener((observableValue, tourDTO, newValue) -> {
            if (newValue.isPresent()) {
                this.tourBasicData.updateTourSuccessful(newValue.get());
                this.tourList.updateTourSuccessful(newValue.get());
            } else {
                this.logger.warn("Tour not updated successfully.");
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
            this.infoLine.setErrorText(ex.getMessage());
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
        Service<List<TourLog>> getMultipleLogService = new GetMultipleLogService(this::getAllTourLogsCatchException, currentTourId);
        getMultipleLogService.valueProperty().addListener((ObservableValue<? extends List<TourLog>> observableValue, List<TourLog> tourDTOS, List<TourLog> newValues) -> {
            if (newValues.isEmpty()) {
                this.logger.warn("No tour logs fetched. Maybe something went wrong.");
            }
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
            this.infoLine.setErrorText(e.getMessage());
        }
        return out;
    }

    /**
     * From {@link TourLogData}
     *
     * @param toAdd
     */
    private void addTourLog(TourLog toAdd) {
        Service<Optional<TourLog>> addUpdateSingleLogService = new AddUpdateSingleLogService(this::addTourLogCatchException, toAdd);
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
            this.infoLine.setErrorText(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * From {@link TourLogData}
     *
     * @param toUpdate
     */
    private void updateTourLog(TourLog toUpdate) {
        Service<Optional<TourLog>> addUpdateSingleLogService = new AddUpdateSingleLogService(this::updateTourCatchException, toUpdate);
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
            this.infoLine.setErrorText(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * From {@link TourLogData}
     *
     * @param toDelete
     */
    private void deleteTourLog(TourLog toDelete) {
        Service<Boolean> deleteSingleLogService = new DeleteSingleLogService(this::deleteTourLogCatchException, toDelete);
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
            this.infoLine.setErrorText(e.getMessage());
            return false;
        }
    }
}
