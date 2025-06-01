package com.maybach7.formhandler.dto;

import lombok.*;

import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationDto {

    private String fullName;
    private String email;
    private String phone;
    private String birthday;
    private String gender;
    private List<String> programmingLanguages;
    private String biography;
}