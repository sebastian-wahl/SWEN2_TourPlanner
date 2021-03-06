package at.fhtw.swen2_tourplanner.backend.tour.dto;

import at.fhtw.swen2_tourplanner.backend.tour.model.Tour;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private double tourDistance;
    @JsonProperty("estimated_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    @JsonProperty("favorite")
    private boolean favorite;
    @JsonProperty("routeImage")
    private byte[] routeImage;
    @JsonProperty("routeImageName")
    private String routeImageName;

    public TourDTO(Tour tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.tourDescription = tour.getTourDescription();
        this.start = tour.getStart();
        this.goal = tour.getGoal();
        this.transportType = tour.getTransportType();
        this.tourDistance = tour.getTourDistance();
        this.estimatedTime = tour.getEstimatedTime();
        this.favorite = tour.isFavorite();
        this.routeImageName = tour.getRouteImageName();
        this.routeImage = tour.getImage();
    }

    public TourDTO(Tour tour, byte[] image) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.tourDescription = tour.getTourDescription();
        this.start = tour.getStart();
        this.goal = tour.getGoal();
        this.transportType = tour.getTransportType();
        this.tourDistance = tour.getTourDistance();
        this.estimatedTime = tour.getEstimatedTime();
        this.favorite = tour.isFavorite();
        this.routeImageName = tour.getRouteImageName();
        this.routeImage = image;
    }
}
