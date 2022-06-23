package at.fhtw.swen2_tourplanner.backend.mapquest.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    @JsonProperty("locations")
    private Location[] locations;
}
