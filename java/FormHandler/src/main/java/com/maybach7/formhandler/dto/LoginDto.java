package com.maybach7.formhandler.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginDto {

    String login;
    String password;
}
