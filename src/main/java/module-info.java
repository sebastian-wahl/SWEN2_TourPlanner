module at.fhtw.swen2_tourplanner.swen2_tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires log4j;
    requires lombok;

    opens at.fhtw.swen2_tourplanner.swen2_tourplanner.controller;
    exports at.fhtw.swen2_tourplanner.swen2_tourplanner.controller;
    opens at.fhtw.swen2_tourplanner.swen2_tourplanner.viewmodel;
    exports at.fhtw.swen2_tourplanner.swen2_tourplanner.viewmodel;
    opens at.fhtw.swen2_tourplanner.swen2_tourplanner to javafx.fxml;
    exports at.fhtw.swen2_tourplanner.swen2_tourplanner;
}