package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class User {

    private Integer id;
    private String fullName;
    private String email;
    private LocalDate birthday;
    private Gender gender;
    private List<Language> languages;
    private String biography;
}