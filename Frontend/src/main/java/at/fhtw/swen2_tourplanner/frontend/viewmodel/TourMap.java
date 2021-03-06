package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.ByteArrayInputStream;

public class TourMap implements ViewModel {

    @Getter
    private final ObjectProperty<Image> imageProperty;
    private Tour currentTour;

    public TourMap() {
        this.imageProperty = new SimpleObjectProperty<>();
    }

    public void setCurrentTour(Tour currentTour) {
        this.currentTour = currentTour;
        this.setUpImage();
    }

    private void setUpImage() {
        if (this.currentTour != null && this.currentTour.getRouteImage() != null) {
            this.imageProperty.setValue(new Image(new ByteArrayInputStream(this.currentTour.getRouteImage())));
        } else {
            this.imageProperty.setValue(null);
        }
    }
}
