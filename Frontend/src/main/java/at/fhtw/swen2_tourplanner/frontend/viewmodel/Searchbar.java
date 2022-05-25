package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.BaseObserver;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchBaseObservable;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Searchbar implements ViewModel, SearchBaseObservable {
    private final ObjectMapper o = new ObjectMapper();
    private List<BaseObserver<String>> searchbarBaseObservers = new ArrayList<>();
    @Getter
    private StringProperty searchText;


    public Searchbar() {
        this.searchText = new SimpleStringProperty();
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
