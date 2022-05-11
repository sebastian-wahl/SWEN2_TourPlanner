package at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;

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
