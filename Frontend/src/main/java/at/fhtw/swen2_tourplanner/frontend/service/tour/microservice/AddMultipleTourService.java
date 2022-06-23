package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Function;

public class AddMultipleTourService extends Service<List<Tour>> {

    private final Function<List<Tour>, List<Tour>> function;
    private final List<Tour> parameter;

    public AddMultipleTourService(Function<List<Tour>, List<Tour>> function, List<Tour> parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<List<Tour>> createTask() {
        return new Task<List<Tour>>() {
            @Override
            protected List<Tour> call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
