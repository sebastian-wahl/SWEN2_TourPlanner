package at.fhtw.swen2_tourplanner.frontend.observer;

public interface StringBooleanObservable {
    void registerObserver(StringBooleanObserver observer);

    void removeObserver(StringBooleanObserver observer);

    void notifyObservers();
}
