package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {

    private Integer userId;
    private String login;
    private String password;
    private String salt;
}
