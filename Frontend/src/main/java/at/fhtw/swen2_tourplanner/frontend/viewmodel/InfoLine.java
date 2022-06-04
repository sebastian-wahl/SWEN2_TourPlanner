package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class InfoLine implements ViewModel {

    @Getter
    private BooleanProperty loadingProperty;

    @Getter
    private StringProperty infoTextProperty;


    public InfoLine() {
        this.loadingProperty = new SimpleBooleanProperty(false);
        this.infoTextProperty = new SimpleStringProperty();
    }
}
