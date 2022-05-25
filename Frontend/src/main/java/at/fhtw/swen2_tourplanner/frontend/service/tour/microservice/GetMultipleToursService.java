package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Supplier;

public class GetMultipleToursService extends Service<List<TourDTO>> {

    private final Supplier<List<TourDTO>> supplier;

    public GetMultipleToursService(Supplier<List<TourDTO>> supplier) {
        this.supplier = supplier;
    }
    @Override
    protected Task<List<TourDTO>> createTask() {
        return new Task<List<TourDTO>>() {
            @Override
            protected List<TourDTO> call() throws Exception {
                return supplier.get();
            }
        };
    }
}
