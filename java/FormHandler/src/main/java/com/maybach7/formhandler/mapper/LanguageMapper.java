package com.maybach7.formhandler.mapper;

import com.maybach7.formhandler.entity.Language;

import java.time.format.DateTimeFormatter;

public class LanguageMapper implements Mapper<Language, String> {

    private final static LanguageMapper INSTANCE = new LanguageMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LanguageMapper getInstance() {
        return INSTANCE;
    }

    private LanguageMapper() {}

    public Language map(String language) {
        return Language.builder()
                .programmingLanguage()
                .build();
    }
}
