package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import at.fhtw.swen2_tourplanner.backend.mapquest.util.Route;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("route")
public class MapQuestResponse {
    @JsonProperty("route")
    private Route route;
}

