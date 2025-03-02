package com.maybach7.formhandler.entity;

import lombok.*;

@Data
@AllArgsConstructor
@ToString
public class Language {

    private Integer id;
    private ProgrammingLanguage programmingLanguage;
}
