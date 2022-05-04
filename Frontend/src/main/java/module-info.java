module at.fhtw.swen2_tourplanner.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires log4j;
    requires lombok;

    opens at.fhtw.swen2_tourplanner.frontend.controller;
    exports at.fhtw.swen2_tourplanner.frontend.controller;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;
    opens at.fhtw.swen2_tourplanner.frontend.cellObjects;
    exports at.fhtw.swen2_tourplanner.frontend.cellObjects;
    opens at.fhtw.swen2_tourplanner.frontend.observers;
    exports at.fhtw.swen2_tourplanner.frontend.observers;
    opens at.fhtw.swen2_tourplanner.frontend to javafx.fxml;
    exports at.fhtw.swen2_tourplanner.frontend;
}