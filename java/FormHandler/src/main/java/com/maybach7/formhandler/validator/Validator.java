package com.maybach7.formhandler.validator;

public interface Validator<T, E> {
    ValidationResult<E> validate(T object);
}