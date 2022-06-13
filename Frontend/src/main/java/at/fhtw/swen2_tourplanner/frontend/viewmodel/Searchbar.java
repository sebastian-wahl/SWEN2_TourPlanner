package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.StringObservable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Searchbar implements ViewModel, StringObservable {
    private final List<BaseObserver<String>> searchbarBaseObservers;
    @Getter
    private final StringProperty searchText;

    @Getter
    private final BooleanProperty inputDisableProperty;

    @Getter
    private final BooleanProperty buttonDisableProperty;


    public Searchbar() {
        this.searchbarBaseObservers = new ArrayList<>();
        this.searchText = new SimpleStringProperty();
        this.inputDisableProperty = new SimpleBooleanProperty(false);
        this.buttonDisableProperty = new SimpleBooleanProperty(false);
    }

    public void disableSearchbar() {
        this.inputDisableProperty.setValue(true);
        this.buttonDisableProperty.setValue(true);
    }

    public void enableSearchbar() {
        this.inputDisableProperty.setValue(false);
        this.buttonDisableProperty.setValue(false);
    }


    public void clearSearch() {
        this.searchText.setValue("");
        this.search();
    }

    @Override
    public void registerObserver(BaseObserver<String> baseObserver) {
        this.searchbarBaseObservers.add(baseObserver);
    }

    @Override
    public void removeObserver(BaseObserver<String> baseObserver) {
        this.searchbarBaseObservers.remove(baseObserver);
    }

    public void search() {
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (BaseObserver<String> baseObserver : this.searchbarBaseObservers) {
            baseObserver.update(searchText.getValue());
        }
    }
}
