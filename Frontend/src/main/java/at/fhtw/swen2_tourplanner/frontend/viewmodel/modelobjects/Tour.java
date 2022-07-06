package at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class Tour {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("start")
    private String start;
    @JsonProperty("tour_description")
    private String tourDescription;
    @JsonProperty("goal")
    private String goal;
    @JsonProperty("transport_type")
    private int transportType;
    @JsonProperty("tour_distance")
    private double tourDistance;
    @JsonProperty("estimated_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    @JsonProperty("favorite")
    private boolean favorite;
    @JsonProperty("routeImage")
    private byte[] routeImage;
    @JsonProperty("routeImageName")
    private String routeImageName;

    // calculated attributes (separate call)
    // 1.0 - 3.0
    @JsonProperty("childFriendliness")
    private double childFriendliness = 3;
    // 1 - 3
    @JsonProperty("popularity")
    private int popularity = 1;

    public Tour(String name) {
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

    public boolean editableEqual(Tour tour) {
        if (isBothNotNull(this.id, tour.id)) {
            if (this.isOneNull(this.id, tour.id) || !this.id.equals(tour.id)) return false;
        }
        if (isBothNotNull(this.name, tour.name)) {
            if (isOneNull(this.name, tour.name) || !this.name.equals(tour.name)) return false;
        }
        if (isBothNotNull(this.start, tour.start)) {
            if (isOneNull(this.start, tour.start) || !this.start.equals(tour.start)) return false;
        }
        if (isBothNotNull(this.goal, tour.goal)) {
            if (this.isOneNull(this.goal, tour.goal) || !this.goal.equals(tour.goal)) {
                return false;
            }
        }
        if (isBothNotNull(this.tourDescription, tour.tourDescription)) {
            if (this.isOneNull(this.tourDescription, tour.tourDescription) || !this.tourDescription.equals(tour.tourDescription)) {
                return false;
            }
        }
        if (this.favorite != tour.isFavorite()) return false;
        return true;
    }

    private boolean isOneNull(Object o1, Object o2) {
        if (o1 == null && o2 != null) return true;
        if (o1 != null && o2 == null) return true;
        return false;
    }

    private boolean isBothNotNull(Object o1, Object o2) {
        if (o1 == null && o2 == null) return false;
        return true;
    }
}
