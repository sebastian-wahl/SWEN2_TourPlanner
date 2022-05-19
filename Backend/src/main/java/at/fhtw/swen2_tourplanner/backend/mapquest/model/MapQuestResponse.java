package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.time.LocalTime;

@Data
@JsonRootName("route")
public class MapQuestResponse {
    @JsonProperty("route")
    private Route route;

    @Data
    private static class Route {
        @JsonProperty("distance")
        private double distance;
        @JsonProperty("formattedTime")
        private LocalTime formattedTime;
    }
}
