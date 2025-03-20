package com.maybach7.formhandler.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult<E> {

    private final List<E> errors = new ArrayList<>();

    public void add(E error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
