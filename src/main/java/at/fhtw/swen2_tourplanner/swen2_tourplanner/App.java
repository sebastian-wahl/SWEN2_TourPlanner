package at.fhtw.swen2_tourplanner.swen2_tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    //static Logger logger = Logger.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/at/fhtw/swen2_tourplanner/swen2_tourplanner/fxml/dashboard.fxml"));
        Parent root = loader.load();
        var scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //logger.info("Starting Tourplanner application");
        launch();
        //logger.info("Finished");
    }
}
