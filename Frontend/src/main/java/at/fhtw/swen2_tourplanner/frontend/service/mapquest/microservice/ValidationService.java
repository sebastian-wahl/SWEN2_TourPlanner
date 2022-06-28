package at.fhtw.swen2_tourplanner.frontend.service.mapquest.microservice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Function;

public class ValidationService extends Service<Boolean> {
    private final Function<String, Boolean> function;
    private final String parameter;

    public ValidationService(Function<String, Boolean> function, String parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}