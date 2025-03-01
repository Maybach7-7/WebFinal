package com.maybach7.formhandler.validator;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Gender;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.util.LocalDateFormatter;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ApplicationValidator implements Validator<ApplicationDto> {

    private final static ApplicationValidator INSTANCE = new ApplicationValidator();

    @Override
    public ValidationResult validate(ApplicationDto dto) {
        var validationResult = new ValidationResult();
        if (!dto.getFullName().matches("/(^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)? [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?)$")) {
            validationResult.add(
                    Error.of("invalid.fullname", "Fullname is invalid")
            );
        }
        if (!dto.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$")) {
            validationResult.add(
                    Error.of("invalid.email", "Email is invalid")
            );
        }
        if (!dto.getPhone().matches("^((\\+7|7|8)+([0-9]){10})$")) {
            validationResult.add(
                    Error.of("invalid.phone", "Phone is invalid")
            );
        }
        if (!LocalDateFormatter.isValid(dto.getBirthday())) {
            validationResult.add(
                    Error.of("invalid.birthday", "Birthday is invalid")
            );
        }
        if (Gender.find(dto.getGender()).isEmpty()) {
            validationResult.add(
                    Error.of("invalid.gender", "Gender is invalid")
            );
        }
        if (dto.getProgrammingLanguages().stream()
                .allMatch(language -> ProgrammingLanguage.find(language).isPresent())){
            validationResult.add(
                    Error.of("invalid.programmingLanguage", "Programming language is invalid")
            );
        }

        return validationResult;
    }
}