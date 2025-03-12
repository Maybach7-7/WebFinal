package com.maybach7.formhandler.exception;

import lombok.Getter;
import com.maybach7.formhandler.validator.Error;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationException extends Exception {

    private List<Error> errors = new ArrayList<>();

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
