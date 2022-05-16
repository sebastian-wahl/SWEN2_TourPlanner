package at.fhtw.swen2_tourplanner.backend.tour.dto;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class TourDTO {
    private UUID id;
    private String name;
    private String tourDescription;
    private String start;
    private String goal;
    private int transportType;
    private long tourDistance;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    private String routeInformation;
    private boolean favorite;

    public Tour toTour() {
        return new Tour(id, name, tourDescription, start, goal, transportType, tourDistance, estimatedTime,
                routeInformation, favorite);
    }
}
