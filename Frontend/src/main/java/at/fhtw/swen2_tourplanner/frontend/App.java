package at.fhtw.swen2_tourplanner.frontend;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Locale;

@Log4j2
public class App extends Application {
    public static void main(String[] args) {
        log.info("Starting Tourplanner application");
        launch();
        log.info("Finished Tourplanner application");
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
