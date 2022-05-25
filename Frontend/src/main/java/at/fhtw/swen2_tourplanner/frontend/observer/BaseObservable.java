package at.fhtw.swen2_tourplanner.frontend.observer;

public interface BaseObservable<T> {
    void registerObserver(BaseObserver<T> baseObserver);
    void removeObserver(BaseObserver<T> baseObserver);
    void notifyObservers();
}
