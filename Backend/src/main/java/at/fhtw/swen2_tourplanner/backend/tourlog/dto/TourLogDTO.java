package at.fhtw.swen2_tourplanner.backend.tourlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
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
}