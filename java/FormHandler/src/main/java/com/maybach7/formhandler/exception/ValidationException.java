package com.maybach7.formhandler.exception;

import com.maybach7.formhandler.validator.InputError;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends Exception {

    private List<InputError> errors = new ArrayList<>();

    public ValidationException(List<InputError> errors) {
        this.errors = errors;
    }
}
