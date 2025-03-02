package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class User {

    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthday;
    private Gender gender;
    private List<ProgrammingLanguage> languages;
    private String biography;
}