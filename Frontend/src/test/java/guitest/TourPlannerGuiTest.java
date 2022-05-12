package guitest;

import at.fhtw.swen2_tourplanner.frontend.FXMLDependencyInjection;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Locale;


@ExtendWith(ApplicationExtension.class)
class TourPlannerGuiTest {

    @Start
    private void start(Stage stage) {
        Parent root = null;  // Locale.GERMANY, Locale.ENGLISH
        try {
            root = FXMLDependencyInjection.load("dashboard.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSearchBarDeleteButton(FxRobot robot) {
        TextField searchbar = robot.lookup("#searchTextField").queryAs(TextField.class);
        searchbar.setText("Hallo");
        robot.clickOn("#clearButton");
        Assertions.assertThat(searchbar).hasText("");
    }

    // not working yet..
    @Test
    void testAddTour(FxRobot robot) {
        TextField tourNameField = robot.lookup("#toAddTourName").queryAs(TextField.class);
        ListView<TourDTO> tourListView = robot.lookup("#tourListView").queryAs(ListView.class);
        int startSize = tourListView.getItems().size();
        tourNameField.setText("New Tour");
        robot.clickOn("#addTourButton");
        Assertions.assertThat(tourNameField).hasText("");
        Assertions.assertThat(tourListView).hasExactlyChildren(startSize + 1, "#cell");

        Button deleteButton = robot.lookup("#deleteButton" + startSize).queryButton();
        robot.clickOn(deleteButton);
    }
}
