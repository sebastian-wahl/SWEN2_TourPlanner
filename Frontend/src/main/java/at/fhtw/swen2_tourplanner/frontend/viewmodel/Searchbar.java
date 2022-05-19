package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.observer.Observer;
import at.fhtw.swen2_tourplanner.frontend.observer.SearchObservable;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Searchbar implements ViewModel, SearchObservable {
    private final ObjectMapper o = new ObjectMapper();
    private List<Observer<String>> searchbarObservers = new ArrayList<>();
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
    public void registerObserver(Observer<String> observer) {
        this.searchbarObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer<String> observer) {
        this.searchbarObservers.remove(observer);
    }

    public void search() {
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer<String> observer : this.searchbarObservers) {
            observer.update(searchText.getValue(), this.getClass());
        }
    }
}
