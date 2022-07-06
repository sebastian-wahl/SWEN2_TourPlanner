package at.fhtw.swen2_tourplanner.frontend.cellObject.converter;

import at.fhtw.swen2_tourplanner.frontend.cellObject.exception.ConverterException;

public interface Converter<T> {
    T convertFromString(String s) throws ConverterException;

    String convertToString(T t) throws ConverterException;
}
