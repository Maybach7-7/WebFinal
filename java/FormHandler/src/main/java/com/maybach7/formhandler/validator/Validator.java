package com.maybach7.formhandler.validator;

public interface Validator<T> {
    ValidationResult validate(T object);
}
