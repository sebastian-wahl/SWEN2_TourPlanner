package at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TourDTO {

    // ToDo think about id
    private long id;
    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
