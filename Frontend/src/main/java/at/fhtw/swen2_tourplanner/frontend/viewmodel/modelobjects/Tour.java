package at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tour {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("start")
    private String start;
    @JsonProperty("tour_description")
    private String tourDescription;
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
    @JsonProperty("routeImage")
    private byte[] routeImage;
    @JsonProperty("routeImageName")
    private String routeImageName;

    public Tour(String name) {
        this.name = name;
        this.favorite = false;
        this.start = "";
        this.goal = "";
        this.transportType = 0;
        this.tourDistance = 0;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
