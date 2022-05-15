package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;

@Data
public class MapQuestResponse {
    @JsonProperty("distance")
    public double distance;
    @JsonProperty("formattedTime")
    public LocalTime formattedTime;
}
