package com.maybach7.formhandler.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@UtilityClass
public class LocalDateFormatter {

    private final static String PATTER = "yyyy-MM-dd";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTER);

    public LocalDate format(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public boolean isValid(String date) {
        try {
            return Optional.ofNullable(date)
                    .map(LocalDateFormatter::format)
                    .isPresent();
        } catch(DateTimeParseException exc) {
            return false;
        }
    }
}
