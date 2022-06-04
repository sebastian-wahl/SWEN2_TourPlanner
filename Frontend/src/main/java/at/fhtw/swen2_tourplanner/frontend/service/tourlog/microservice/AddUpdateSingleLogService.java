package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Optional;
import java.util.function.Function;

public class AddUpdateSingleLogService extends Service<Optional<TourLog>> {
    private final Function<TourLog, Optional<TourLog>> function;
    private final TourLog parameter;

    public AddUpdateSingleLogService(Function<TourLog, Optional<TourLog>> function, TourLog parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Optional<TourLog>> createTask() {
        return new Task<>() {
            @Override
            protected Optional<TourLog> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
