package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.service.TourLogService;
import at.fhtw.swen2_tourplanner.frontend.service.TourService;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.*;

public class ControllerFactory {
    private final Dashboard dashboard;
    private final Menubar menubar;
    private final Searchbar searchbar;
    private final TourInfo tourInfo;
    private final TourList tourList;
    private final TourBasicData tourBasicData;

    // services
    private final TourService tourService;
    private final TourLogService tourLogService;

    public ControllerFactory() {
        tourService = new TourService();
        tourLogService = new TourLogService();

        menubar = new Menubar();
        searchbar = new Searchbar();
        tourBasicData = new TourBasicData();
        tourList = new TourList();
        tourInfo = new TourInfo(tourBasicData);

        // --- register observers ---
        // search observer
        searchbar.registerObserver(tourList);
        // tourlist selection observer
        tourList.registerObserver(tourInfo);

        dashboard = new Dashboard(tourList, tourInfo);
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
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static final ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
