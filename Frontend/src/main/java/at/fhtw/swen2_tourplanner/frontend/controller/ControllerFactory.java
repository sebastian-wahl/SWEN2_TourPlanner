package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.service.MapService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourServiceImpl;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogServiceImpl;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.*;

public class ControllerFactory {
    //
    // Singleton-Pattern with early-binding
    //
    private static final ControllerFactory instance = new ControllerFactory();
    // ViewModels
    private final Dashboard dashboard;
    private final Menubar menubar;
    private final Searchbar searchbarTour;
    private final Searchbar searchbarTourLog;
    private final TourInfo tourInfo;
    private final TourList tourList;
    private final TourBasicData tourBasicData;
    private final TourLogData tourLogData;
    private final TourMap tourMap;
    private final InfoLine infoLine;

    // services
    private final TourService tourService;
    private final TourLogService tourLogService;
    private final MapService mapService;

    public ControllerFactory() {
        // services
        tourService = new TourServiceImpl();
        tourLogService = new TourLogServiceImpl();
        mapService = new MapService();

        // views
        tourMap = new TourMap();
        menubar = new Menubar();
        searchbarTour = new Searchbar();
        tourBasicData = new TourBasicData();
        tourInfo = new TourInfo(tourBasicData, tourMap);
        infoLine = new InfoLine();
        searchbarTourLog = new Searchbar();
        tourLogData = new TourLogData(searchbarTourLog, infoLine);
        tourList = new TourList();
        dashboard = new Dashboard(tourList, tourBasicData, tourMap, tourLogData, infoLine, tourService, tourLogService);


        // --- register observers ---
        // search observer
        searchbarTour.registerObserver(tourList);
        searchbarTourLog.registerObserver(tourLogData);
        // tourlist selection observer
        tourList.registerObserver(tourInfo::updateFromTourList);
        tourList.registerObserver(tourLogData::updateFromTourList);
        // update tour observer (update details)
        tourBasicData.registerObserver(tourInfo::updateFromTourEditOperation);
        // string boolean observer (for updating the info line)


    }

    public static ControllerFactory getInstance() {
        return instance;
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
        if (controllerClass == DashboardController.class) {
            return new DashboardController(this.dashboard);
        } else if (controllerClass == TourSearchbarController.class) {
            return new TourSearchbarController(this.searchbarTour);
        } else if (controllerClass == MenubarController.class) {
            return new MenubarController(this.menubar);
        } else if (controllerClass == TourInfoController.class) {
            return new TourInfoController(this.tourInfo);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(this.tourList);
        } else if (controllerClass == TourBasicDataController.class) {
            return new TourBasicDataController(this.tourBasicData);
        } else if (controllerClass == TourLogDataController.class) {
            return new TourLogDataController(this.tourLogData);
        } else if (controllerClass == TourMapController.class) {
            return new TourMapController(this.tourMap);
        } else if (controllerClass == TourLogSearchbarController.class) {
            return new TourLogSearchbarController(this.searchbarTourLog);
        } else if (controllerClass == SearchbarBaseController.class) {
            return new SearchbarBaseController();
        } else if (controllerClass == InfoLineController.class) {
            return new InfoLineController(infoLine);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
}
