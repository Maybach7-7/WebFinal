package com.maybach7.formhandler.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class InputError {

    String id;
    String message;
}