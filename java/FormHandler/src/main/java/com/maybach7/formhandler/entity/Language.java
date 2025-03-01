package com.maybach7.formhandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
@Builder
public class Language {

    private Integer id;
    private ProgrammingLanguage programmingLanguage;
}
