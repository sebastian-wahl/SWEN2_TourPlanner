module at.fhtw.swen2_tourplanner.swen2_tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens at.fhtw.swen2_tourplanner.swen2_tourplanner to javafx.fxml;
    exports at.fhtw.swen2_tourplanner.swen2_tourplanner;
}