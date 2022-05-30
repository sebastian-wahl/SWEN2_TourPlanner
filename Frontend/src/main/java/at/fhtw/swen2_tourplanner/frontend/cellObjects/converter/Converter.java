package at.fhtw.swen2_tourplanner.frontend.cellObjects.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;

public interface Converter<T> {
    T convertFromString(String s) throws ConverterException;

    String convertToString(T t) throws ConverterException;
}
