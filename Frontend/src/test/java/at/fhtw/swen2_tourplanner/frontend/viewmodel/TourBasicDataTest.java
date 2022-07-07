package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.customEnum.TransportTypeEnum;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TourBasicDataTest {

    private final TourBasicData tourBasicData = new TourBasicData();
    private final DecimalFormat df = new DecimalFormat("##." + "0".repeat(2));

    @Test
    void testSetCurrentTourToTour() {
        Tour testTour = new Tour("Test");
        tourBasicData.setCurrentTour(testTour);
        assertThat(tourBasicData.getNameProperty().getValue()).isEqualTo(testTour.getName());
        assertThat(tourBasicData.getFromProperty().getValue()).isEqualTo(testTour.getStart());
        assertThat(tourBasicData.getToProperty().getValue()).isEqualTo(testTour.getGoal());
        assertThat(tourBasicData.getDistanceProperty().getValue()).isEqualTo(testTour.getTourDistance() + " km");
        assertThat(tourBasicData.getDescriptionProperty().getValue()).isEqualTo(testTour.getTourDescription());
        assertThat(tourBasicData.getEstimatedTimeProperty().getValue()).isEqualTo("00:00:00 (HH:MM:SS)");
        assertThat(tourBasicData.getPopularityProperty().getValue()).isEqualTo(testTour.getPopularity() + "/3");
        assertThat(tourBasicData.getChildFriendlinessProperty().getValue()).isEqualTo(df.format(testTour.getChildFriendliness()).replace(",", ".") + "/3.00");
        assertThat(tourBasicData.getFavoriteCheckboxProperty().getValue()).isEqualTo(testTour.isFavorite());
        assertThat(tourBasicData.getTransportTypeSelectedItemProperty().getValue()).isEqualTo(TransportTypeEnum.valueOf(testTour.getTransportType()).getName());
    }

    @Test
    void testSetCurrentTourToNull() {
        tourBasicData.setCurrentTour(null);
        assertThat(tourBasicData.getNameProperty().getValue()).isEmpty();
        assertThat(tourBasicData.getNameProperty().getValue()).isEmpty();
        assertThat(tourBasicData.getFromProperty().getValue()).isEmpty();
        assertThat(tourBasicData.getToProperty().getValue()).isEmpty();
        assertThat(tourBasicData.getDistanceProperty().getValue()).isEqualTo("");
        assertThat(tourBasicData.getDescriptionProperty().getValue()).isEqualTo("");
        assertThat(tourBasicData.getEstimatedTimeProperty().getValue()).isEqualTo("");
        assertThat(tourBasicData.getPopularityProperty().getValue()).isEqualTo("");
        assertThat(tourBasicData.getChildFriendlinessProperty().getValue()).isEqualTo("");
        assertThat(tourBasicData.getFavoriteCheckboxProperty().getValue()).isEqualTo(false);
        assertThat(tourBasicData.getTransportTypeSelectedItemProperty().getValue()).isEqualTo("");
    }


}