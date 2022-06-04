package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Optional;
import java.util.function.Function;

public class AddUpdateSingleTourService extends Service<Optional<Tour>> {
    private final Function<Tour, Optional<Tour>> function;
    private final Tour parameter;

    public AddUpdateSingleTourService(Function<Tour, Optional<Tour>> function, Tour parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Optional<Tour>> createTask() {
        return new Task<>() {
            @Override
            protected Optional<Tour> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
