package at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourLog {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("number")
    private int number;
    @JsonProperty("date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("difficulty")
    private int difficulty;
    @JsonProperty("total_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime totalTime;
    @JsonProperty("rating")
    private double rating;

    @JsonProperty("tour_id")
    private UUID tour;

    public TourLog(Tour tour) {
        this.difficulty = 1;
        this.rating = 1;
        this.tour = tour.getId();
    }
}
