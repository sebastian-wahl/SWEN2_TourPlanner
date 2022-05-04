package at.fhtw.swen2_tourplanner.backend.tourlog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "tour_log")
public class TourLog {
    @Id
    private long id;
    private LocalDateTime dateTime;
    private String comment;
    private int difficulty;
    private LocalTime totalTime;
    private double rating;
    //TODO: Add reference to tours
}
