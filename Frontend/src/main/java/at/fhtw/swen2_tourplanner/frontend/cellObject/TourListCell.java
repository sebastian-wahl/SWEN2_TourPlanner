package at.fhtw.swen2_tourplanner.frontend.cellObject;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public class TourListCell extends ListCell<Tour> {
    private final Consumer<UUID> deleteTourConsumer;
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane cell;
    @FXML
    private Button deleteTourButton;
    private FXMLLoader mLLoader;

    public TourListCell(Consumer<UUID> deleteTourConsumer) {
        this.deleteTourConsumer = deleteTourConsumer;
    }

    @Override
    protected void updateItem(Tour tour, boolean empty) {
        super.updateItem(tour, empty);

        if (empty || tour == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/at.fhtw.swen2_tourplanner.fxml/list-view-cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            deleteTourButton.setOnMouseClicked(event -> this.deleteTourConsumer.accept(tour.getId()));
            nameLabel.setText(tour.getName());
            setGraphic(cell);
        }

    }
}
