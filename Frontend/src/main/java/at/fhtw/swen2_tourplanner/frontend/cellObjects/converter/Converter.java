package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

public interface Converter<T> {
    T fromString(String s);

    String toString(T t);
}
