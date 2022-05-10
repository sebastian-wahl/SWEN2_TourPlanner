package at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TourDTO {

    // ToDo think about id
    private UUID id;
    private String name;
    private String start;
    private String tourDescription;
    private String goal;
    private int transportType;
    private long tourDistance;
    private LocalTime estimatedTime;
    private String routeInformation;
    private boolean favorite;

    public TourDTO(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.favorite = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
