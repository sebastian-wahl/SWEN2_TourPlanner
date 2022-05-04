package at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.TourInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TourDTO {

    // ToDo think about id
    private UUID id;
    private String name;

    private boolean isFavorite;

    public TourDTO(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.isFavorite = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
