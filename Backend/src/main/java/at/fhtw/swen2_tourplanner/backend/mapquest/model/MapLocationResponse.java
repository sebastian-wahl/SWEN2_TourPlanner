package at.fhtw.swen2_tourplanner.backend.mapquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("results")
public class MapLocationResponse {
    @JsonProperty("locations")
    private Locations locations;

    @Data
    public class Locations {
        @JsonProperty("street")
        private String street;
        @JsonProperty("adminArea3")
        private String state;
        @JsonProperty("adminArea1")
        private String country;
        @JsonProperty("postalCode")
        private String postalCode;
    }
}