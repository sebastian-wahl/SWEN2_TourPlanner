module at.fhtw.swen2_tourplanner.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires io.reactivex.rxjava3;

    opens at.fhtw.swen2_tourplanner.frontend.controller;
    exports at.fhtw.swen2_tourplanner.frontend.controller;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;
    opens at.fhtw.swen2_tourplanner.frontend.cellObjects;
    exports at.fhtw.swen2_tourplanner.frontend.cellObjects;
    opens at.fhtw.swen2_tourplanner.frontend.observer;
    exports at.fhtw.swen2_tourplanner.frontend.observer;
    opens at.fhtw.swen2_tourplanner.frontend.service;
    exports at.fhtw.swen2_tourplanner.frontend.service;
    opens at.fhtw.swen2_tourplanner.frontend to javafx.fxml;
    exports at.fhtw.swen2_tourplanner.frontend;
    exports at.fhtw.swen2_tourplanner.frontend.service.tour;
    opens at.fhtw.swen2_tourplanner.frontend.service.tour;
}