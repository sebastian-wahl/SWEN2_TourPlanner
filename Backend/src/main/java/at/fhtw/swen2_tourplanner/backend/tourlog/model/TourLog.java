package at.fhtw.swen2_tourplanner.backend.tourlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tour_log", schema = "tour")
public class TourLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "comment")
    private String comment;
    @Column(name = "difficulty", nullable = false)
    private int difficulty;
    @Column(name = "total_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime totalTime;
    @Column(name = "rating", nullable = false)
    private double rating;
    //TODO: Add reference to tours
}
