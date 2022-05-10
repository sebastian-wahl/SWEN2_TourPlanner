package at.fhtw.swen2_tourplanner.frontend.observer;

/**
 * @param <T> context of this observer
 */
public interface Observer<T> {
    public void update(T t);
}
