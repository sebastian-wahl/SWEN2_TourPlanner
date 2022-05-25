package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Function;

public class DeleteSingleTourService extends Service<Boolean> {
    private final Function<TourDTO, Boolean> function;
    private final TourDTO parameter;

    public DeleteSingleTourService(Function<TourDTO, Boolean> function, TourDTO parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
