package at.fhtw.swen2_tourplanner.frontend.observer;

/**
 * @param <T> context of this observer
 */
public interface BaseObserver<T> {
    void update(T t);
}
