package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.service.MapService;
import at.fhtw.swen2_tourplanner.frontend.service.tourlog.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.tour.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.*;

public class ControllerFactory {
    //
    // Singleton-Pattern with early-binding
    //
    private static final ControllerFactory instance = new ControllerFactory();
    // ViewModels
    private final Dashboard dashboard;
    private final Menubar menubar;
    private final Searchbar searchbar;
    private final TourInfo tourInfo;
    private final TourList tourList;
    private final TourBasicData tourBasicData;
    private final TourLogData tourLogData;
    private final TourMap tourMap;

    // services
    private final TourService tourService;
    private final TourLogService tourLogService;
    private final MapService mapService;

    public ControllerFactory() {
        // services
        tourService = new TourService();
        tourLogService = new TourLogService();
        mapService = new MapService();

        // views
        tourMap = new TourMap();
        menubar = new Menubar();
        searchbar = new Searchbar();
        tourLogData = new TourLogData(tourLogService);
        tourBasicData = new TourBasicData(tourService);
        tourList = new TourList(tourService);
        tourInfo = new TourInfo(tourBasicData, tourMap);

        // --- register observers ---
        // search observer
        searchbar.registerObserver(tourList);
        // tourlist selection observer
        tourList.registerObserver(tourInfo::updateFromTourList);
        tourList.registerObserver(tourLogData::updateFromTourList);
        // update tour observer (update details)
        tourBasicData.registerObserver(tourInfo::updateFromTourEditOperation);

        dashboard = new Dashboard(tourList, tourInfo);
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
        } else if (controllerClass == SearchbarController.class) {
            return new SearchbarController(this.searchbar);
        } else if (controllerClass == MenubarController.class) {
            return new MenubarController(this.menubar);
        } else if (controllerClass == TourInfoController.class) {
            return new TourInfoController(this.tourInfo);
        } else if (controllerClass == TourListController.class) {
            return new TourListController(this.tourList, this.searchbar);
        } else if (controllerClass == TourBasicDataController.class) {
            return new TourBasicDataController(this.tourBasicData);
        } else if (controllerClass == TourLogDataController.class) {
            return new TourLogDataController(this.tourLogData);
        } else if (controllerClass == TourMapController.class) {
            return new TourMapController(this.tourMap);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
}
