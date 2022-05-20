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
    @Column(name = "start", nullable = false)
    private String start;
    @Column(name = "goal", nullable = false)
    private String goal;
    @Column(name = "transport_type", nullable = false)
    private int transportType;
    @Column(name = "tour_distance", nullable = false)
    private long tourDistance;
    @Column(name = "estimated_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedTime;
    @Column(name = "route_information")
    private String routeInformation;
    @Column(name = "favorite", nullable = false, columnDefinition = "boolean default false")
    private boolean favorite;
    @Column(name = "routeImageName")
    private String routeImageName;

    public Tour(TourDTO tourDTO) {
        this.id = tourDTO.getId();
        this.name = tourDTO.getName();
        this.tourDescription = tourDTO.getTourDescription();
        this.start = tourDTO.getStart();
        this.goal = tourDTO.getGoal();
        this.transportType = tourDTO.getTransportType();
        this.tourDistance = tourDTO.getTourDistance();
        this.estimatedTime = tourDTO.getEstimatedTime();
        this.routeInformation = tourDTO.getRouteInformation();
        this.favorite = tourDTO.isFavorite();
        this.routeImageName = tourDTO.getRouteImageName();
    }
}

/*
EXAMPLE JSON FOR POST REQUEST
{
    OPTIONAL (ONLY IN CASE OF UPDATE) "id": "95be5aec-b7ec-4c1e-b04f-5d8d4159c192",
    "name": "Tour1",
    "tourDescription": "this is the description",
    "start": "Spain",
    "goal": "France",
    "transportType": "1",
    "tourDistance": "1",
    "estimatedTime": "06:00:00",
    "routeInformation": "this is the information"
}
*/
