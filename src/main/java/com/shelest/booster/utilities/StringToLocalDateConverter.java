package com.shelest.booster.utilities;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * Created by Home on 27.09.2017.
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return null;
    }
}
