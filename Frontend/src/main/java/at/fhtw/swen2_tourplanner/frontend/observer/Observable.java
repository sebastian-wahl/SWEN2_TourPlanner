package at.fhtw.swen2_tourplanner.frontend.observer;

public interface Observable<T> {
    void registerObserver(Observer<T> observer);
    void removeObserver(Observer<T> observer);
    void notifyObservers();
}
