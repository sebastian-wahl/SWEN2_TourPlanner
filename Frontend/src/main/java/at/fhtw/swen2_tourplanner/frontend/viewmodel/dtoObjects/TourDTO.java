package at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TourDTO {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("start")
    private String start;
    @JsonProperty("tourDescription")
    private String tourDescription;
    @JsonProperty("goal")
    private String goal;
    @JsonProperty("transportType")
    private int transportType;
    @JsonProperty("tourDistance")
    private long tourDistance;
    @JsonProperty("estimatedTime")
    private LocalTime estimatedTime;
    @JsonProperty("routeInformation")
    private String routeInformation;
    @JsonProperty("favorite")
    private boolean favorite;

    public TourDTO(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.favorite = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
