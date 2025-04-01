package com.maybach7.formhandler.exception;

import com.maybach7.formhandler.validator.InputError;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LoginException extends Exception {

    private List<InputError> errors = new ArrayList<>();

    public LoginException() {
        errors.add(InputError.of("LoginOrPassword", "Логин или пароль введены неверно!"));
    }
}