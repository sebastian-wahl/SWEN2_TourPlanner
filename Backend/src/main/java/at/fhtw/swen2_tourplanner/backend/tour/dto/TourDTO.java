package at.fhtw.swen2_tourplanner.backend.tour.dto;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class TourDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tour_description")
    private String tourDescription;
    @JsonProperty("start")
    private String start;
    @JsonProperty("goal")
    private String goal;
    @JsonProperty("transport_type")
    private int transportType;
    @JsonProperty("tour_distance")
    private long tourDistance;
    @JsonProperty("estimated_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    @JsonProperty("route_information")
    private String routeInformation;
    @JsonProperty("favorite")
    private boolean favorite;

    public TourDTO(Tour tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.tourDescription = tour.getTourDescription();
        this.start = tour.getStart();
        this.goal = tour.getGoal();
        this.transportType = tour.getTransportType();
        this.tourDistance = tour.getTourDistance();
        this.estimatedTime = tour.getEstimatedTime();
        this.routeInformation = tour.getRouteInformation();
        this.favorite = tour.isFavorite();
    }
}
