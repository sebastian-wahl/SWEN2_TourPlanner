package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class GetMultipleLogService extends Service<List<TourLogDTO>> {

    private final Function<UUID, List<TourLogDTO>> function;
    private final UUID parameter;

    public GetMultipleLogService(Function<UUID, List<TourLogDTO>> function, UUID parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<List<TourLogDTO>> createTask() {
        return new Task<List<TourLogDTO>>() {
            @Override
            protected List<TourLogDTO> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
