package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Function;

public class DeleteSingleLogService extends Service<Boolean> {

    private final Function<TourLog, Boolean> function;
    private final TourLog parameter;

    public DeleteSingleLogService(Function<TourLog, Boolean> function, TourLog parameter) {
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
