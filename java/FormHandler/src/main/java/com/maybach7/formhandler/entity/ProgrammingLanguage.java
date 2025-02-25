package com.maybach7.formhandler.entity;

import java.util.Arrays;
import java.util.Optional;

public enum ProgrammingLanguage {

    PASCAL("Pascal"),
    C("C"),
    CPULSPLUS("C++"),
    JAVASCRIPT("Javascript"),
    PHP("PHP"),
    PYTHON("Python"),
    JAVA("Java"),
    HASKEL("Haskel"),
    CLOJURE("Clojure"),
    PROLOG("Prolog"),
    SCALA("Scala"),
    GO("Go");

    private final String name;

    ProgrammingLanguage(String language) {
        this.name = language.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public Optional<ProgrammingLanguage> find(String language) {
        return Arrays.stream(values())
                .filter(el -> el.getName().equalsIgnoreCase(language))
                .findFirst();
    }
}
