package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class TourList implements ViewModel {
    @Getter
    private ObservableList<TourDTO> tourList;

    public TourList() {
        tourList = FXCollections.observableArrayList();
        tourList.add(new TourDTO(1, "Hallo"));
        // ToDo BE call for tour lists
    }

    public void addTour(String name) {
        TourDTO newTour = new TourDTO(1, name);
        tourList.add(newTour);
    }

    public void deleteTour(long id) {
        int index = findIndexOfTourById(id);
        if (index >= 0) {
            tourList.remove(index);
        }
    }

    private int findIndexOfTourById(long id) {
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
