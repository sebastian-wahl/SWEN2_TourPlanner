package at.fhtw.swen2_tourplanner.frontend.cellObjects;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public class TourListCell extends ListCell<TourDTO> {
    @FXML
    private Label nameLabel;
    @FXML
    private GridPane cell;
    @FXML
    private Button deleteButton;

    private FXMLLoader mLLoader;

    private final Consumer<UUID> deleteTourConsumer;

    public TourListCell(Consumer<UUID> deleteTourConsumer) {
        this.deleteTourConsumer = deleteTourConsumer;
    }

    @Override
    protected void updateItem(TourDTO tour, boolean empty) {
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

            deleteButton.setOnMouseClicked(event -> this.deleteTourConsumer.accept(tour.getId()));
            nameLabel.setText(tour.getName());
            setGraphic(cell);
        }

    }
}
