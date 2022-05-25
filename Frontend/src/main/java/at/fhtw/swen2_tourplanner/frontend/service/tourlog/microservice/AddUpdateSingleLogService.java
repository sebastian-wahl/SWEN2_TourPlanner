package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Optional;
import java.util.function.Function;

public class AddUpdateSingleLogService extends Service<Optional<TourLogDTO>> {
    private final Function<TourLogDTO, Optional<TourLogDTO>> function;
    private final TourLogDTO parameter;

    public AddUpdateSingleLogService(Function<TourLogDTO, Optional<TourLogDTO>> function, TourLogDTO parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Optional<TourLogDTO>> createTask() {
        return new Task<>() {
            @Override
            protected Optional<TourLogDTO> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
