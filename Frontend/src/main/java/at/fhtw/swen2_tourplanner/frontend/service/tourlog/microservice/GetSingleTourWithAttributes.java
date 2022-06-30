package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class GetSingleTourWithAttributes extends Service<Optional<Tour>> {
    private final Function<UUID, Optional<Tour>> function;
    private final UUID parameter;

    public GetSingleTourWithAttributes(Function<UUID, Optional<Tour>> function, UUID parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Optional<Tour>> createTask() {
        return new Task<Optional<Tour>>() {
            @Override
            protected Optional<Tour> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
