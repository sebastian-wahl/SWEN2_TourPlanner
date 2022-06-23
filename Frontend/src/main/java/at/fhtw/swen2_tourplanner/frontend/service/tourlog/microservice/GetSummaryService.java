package at.fhtw.swen2_tourplanner.frontend.service.tourlog.microservice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Supplier;

public class GetSummaryService extends Service<byte[]> {
    private final Supplier<byte[]> supplier;

    public GetSummaryService(Supplier<byte[]> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected Task<byte[]> createTask() {
        return new Task<byte[]>() {
            @Override
            protected byte[] call() throws Exception {
                return supplier.get();
            }
        };
    }
}
