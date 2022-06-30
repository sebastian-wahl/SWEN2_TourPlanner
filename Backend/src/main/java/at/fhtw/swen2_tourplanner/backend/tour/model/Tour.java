package at.fhtw.swen2_tourplanner.backend.tour.model;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;


//DataModel for Tour Database
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "tour", schema = "tour")
public class Tour {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "tour_description")
    private String tourDescription;
    @Column(name = "start")
    private String start;
    @Column(name = "goal")
    private String goal;
    @Column(name = "transport_type", nullable = false, columnDefinition = "integer default 0")
    private int transportType;
    @Column(name = "tour_distance", nullable = false, columnDefinition = "float8 default 0")
    private double tourDistance;
    @Column(name = "estimated_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    @Column(name = "favorite", nullable = false, columnDefinition = "boolean default false")
    private boolean favorite;
    @Column(name = "routeImageName")
    private String routeImageName;

    @Transient
    private byte[] image;


    public Tour(TourDTO tourDTO) {
        this.id = tourDTO.getId();
        this.name = tourDTO.getName();
        this.tourDescription = tourDTO.getTourDescription();
        this.start = tourDTO.getStart();
        this.goal = tourDTO.getGoal();
        this.transportType = tourDTO.getTransportType();
        this.tourDistance = tourDTO.getTourDistance();
        this.estimatedTime = tourDTO.getEstimatedTime();
        this.favorite = tourDTO.isFavorite();
        this.routeImageName = tourDTO.getRouteImageName();
    }
}
