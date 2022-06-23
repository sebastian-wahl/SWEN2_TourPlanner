package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import at.fhtw.swen2_tourplanner.backend.mapquest.util.Result;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MapLocationResponse {
    @JsonProperty("results")
    private Result[] results;
}

