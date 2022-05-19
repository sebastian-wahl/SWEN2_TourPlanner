package at.fhtw.swen2_tourplanner.backend.tourlog.dto;

import at.fhtw.swen2_tourplanner.backend.tourlog.model.TourLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourLogDTO {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("difficulty")
    private int difficulty;
    @JsonProperty("total_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime totalTime;
    @JsonProperty("rating")
    private double rating;
    @JsonProperty("tour_id")
    private UUID tour;

    public TourLogDTO(TourLog tourLog) {
        this.id = tourLog.getId();
        this.dateTime = tourLog.getDateTime();
        this.comment = tourLog.getComment();
        this.difficulty = tourLog.getDifficulty();
        this.totalTime = tourLog.getTotalTime();
        this.rating = tourLog.getRating();
        this.tour = tourLog.getTour().getId();
    }
}