package com.maybach7.formhandler.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {

    private String login;
    private String password;
}
