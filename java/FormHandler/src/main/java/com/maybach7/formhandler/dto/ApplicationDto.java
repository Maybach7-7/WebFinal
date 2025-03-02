package com.maybach7.formhandler.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString
public class ApplicationDto {

    String fullName;
    String email;
    String phone;
    String birthday;
    String gender;
    List<String> programmingLanguages;
    String biography;
}