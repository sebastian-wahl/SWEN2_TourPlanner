package at.fhtw.swen2_tourplanner.frontend.service.tour.microservice;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Supplier;

public class GetScheduledService extends ScheduledService<List<Tour>> {
    private final Supplier<List<Tour>> supplier;

    public GetScheduledService(Supplier<List<Tour>> supplier) {
        this.supplier = supplier;
    }
    @Override
    protected Task<List<Tour>> createTask() {
        return new Task<List<Tour>>() {
            @Override
            protected List<Tour> call() throws Exception {
                return supplier.get();
            }
        };
    }
}
