package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class GetMultipleLogService extends Service<List<TourLog>> {

    private final Function<UUID, List<TourLog>> function;
    private final UUID parameter;

    public GetMultipleLogService(Function<UUID, List<TourLog>> function, UUID parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<List<TourLog>> createTask() {
        return new Task<List<TourLog>>() {
            @Override
            protected List<TourLog> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
