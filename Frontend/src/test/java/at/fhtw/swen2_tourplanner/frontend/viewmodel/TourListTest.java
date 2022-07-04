package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
@ExtendWith(MockitoExtension.class)
class TourListTest {

    private TourList tourList;

    @BeforeEach
    void setUp() {
        tourList = new TourList();
    }

    @Test
    void testAddTourSuccessful() {
        tourList.getNewTourName().setValue("New Tour");
        tourList.setTourAddListener((Tour tour) -> assertThat(tour).isNotNull());
        tourList.addTour();
    }

    @Test
    void testDeleteTour() {
        Tour testTour = new Tour("Test");
        testTour.setId(UUID.randomUUID());
        tourList.getBaseTourList().add(testTour);
        tourList.setTourDeleteListener((Tour toDelete) -> {
            assertThat(toDelete.getId()).isEqualTo(testTour.getId());
            tourList.deleteTourSuccessful(toDelete);
        });
        tourList.deleteTour(testTour.getId());

        assertThat(tourList.getTourList().size()).isZero();
    }
}