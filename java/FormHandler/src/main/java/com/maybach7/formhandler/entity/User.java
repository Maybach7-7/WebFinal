package com.maybach7.formhandler.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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