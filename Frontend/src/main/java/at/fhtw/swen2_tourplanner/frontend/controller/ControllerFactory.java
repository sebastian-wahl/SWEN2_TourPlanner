package at.fhtw.swen2_tourplanner.frontend.controller;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.*;

public class ControllerFactory {
    private final Dashboard dashboard;
    private final Menubar menubar;
    private final Searchbar searchbar;
    private final TourInfo tourInfo;
    private final TourList tourList;

    public ControllerFactory() {
        dashboard = new Dashboard();
        menubar = new Menubar();
        searchbar = new Searchbar();
        tourInfo = new TourInfo();
        tourList = new TourList();
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
            return new TourListController(this.tourList);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
