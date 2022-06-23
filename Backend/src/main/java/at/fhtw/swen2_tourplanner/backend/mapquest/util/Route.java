package at.fhtw.swen2_tourplanner.backend.mapquest.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Route {
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("formattedTime")
    private LocalTime formattedTime;
}
