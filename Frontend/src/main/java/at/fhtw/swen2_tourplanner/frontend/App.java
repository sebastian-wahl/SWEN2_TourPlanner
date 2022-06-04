package at.fhtw.swen2_tourplanner.frontend;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

public class App extends Application {
    static Logger logger = LogManager.getLogger(App.class);

    // ToDo Http request retrofit
    public static void main(String[] args) {
        logger.info("Starting Tourplanner application");
        launch();
        logger.info("Finished Tourplanner application");
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("dashboard.fxml", Locale.GERMAN);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }
}
