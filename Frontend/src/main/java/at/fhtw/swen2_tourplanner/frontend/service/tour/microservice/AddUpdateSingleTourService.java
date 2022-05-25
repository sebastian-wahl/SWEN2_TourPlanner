package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Optional;
import java.util.function.Function;

public class AddUpdateSingleTourService extends Service<Optional<TourDTO>> {
    private final Function<TourDTO, Optional<TourDTO>> function;
    private final TourDTO parameter;

    public AddUpdateSingleTourService(Function<TourDTO, Optional<TourDTO>> function, TourDTO parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Optional<TourDTO>> createTask() {
        return new Task<>() {
            @Override
            protected Optional<TourDTO> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
