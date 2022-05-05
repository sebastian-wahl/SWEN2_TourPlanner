package at.fhtw.swen2_tourplanner.backend.tour.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.UUID;


//DataModel for Tour Database
@Entity
@Data
@Table(name = "tour", schema="tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}

/*
EXAMPLE JSON FOR POST REQUEST
{
    OPTIONAL (ONLY IN CASE OF UPDATE) "id": "1",
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
