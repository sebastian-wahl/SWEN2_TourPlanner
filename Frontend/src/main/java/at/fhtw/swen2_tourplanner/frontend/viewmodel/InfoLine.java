package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;

public class InfoLine implements ViewModel {

    @Getter
    private final BooleanProperty loadingProperty;

    @Getter
    private final StringProperty infoTextProperty;


    public InfoLine() {
        this.loadingProperty = new SimpleBooleanProperty(false);
        this.infoTextProperty = new SimpleStringProperty();
    }

    public void startLoading() {
        this.loadingProperty.setValue(true);
    }

    public void stopLoading() {
        this.loadingProperty.setValue(false);
    }

    public void setInfoText(String text) {
        Service<Void> setTextService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> infoTextProperty.setValue(text));
                        // sleep for 10 sec then remove text again
                        Thread.sleep(10000);
                        Platform.runLater(() -> {
                            if (infoTextProperty.getValue().equals(text)) {
                                infoTextProperty.setValue("");
                            }
                        });
                        return null;
                    }
                };
            }
        };

        setTextService.start();
    }
}
