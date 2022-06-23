package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.UUID;
import java.util.function.Function;

public class GetReportService extends Service<byte[]> {

    private final Function<UUID, byte[]> function;
    private final UUID parameter;

    public GetReportService(Function<UUID, byte[]> function, UUID parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    @Override
    protected Task<byte[]> createTask() {
        return new Task<byte[]>() {
            @Override
            protected byte[] call() throws Exception {
                return function.apply(parameter);
            }
        };
    }
}
