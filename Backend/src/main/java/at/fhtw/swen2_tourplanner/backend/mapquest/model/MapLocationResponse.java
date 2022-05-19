package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MapLocationResponse {
    @JsonProperty("street")
    private String street;
    @JsonProperty("adminArea3")
    private String state;
    @JsonProperty("adminArea1")
    private String country;
    @JsonProperty("postalCode")
    private String postalCode;
}
