package at.fhtw.swen2_tourplanner.frontend;

import at.fhtw.swen2_tourplanner.frontend.controller.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjection {
    public static Parent load(String location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location) {
        return new FXMLLoader(
                FXMLDependencyInjection.class.getResource("/at.fhtw.swen2_tourplanner.fxml/" + location),
                null,
                new JavaFXBuilderFactory(),
                controllerClass-> ControllerFactory.getInstance().create(controllerClass)
        );
    }
}
