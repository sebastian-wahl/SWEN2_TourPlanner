package at.fhtw.swen2_tourplanner.frontend.listener;

public interface AddListener<T> {
    void addTour(T toAdd, boolean replacement);
}
