package com.maybach7.formhandler.validator;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Gender;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.util.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationValidator implements Validator<ApplicationDto, InputError> {

    private final static ApplicationValidator INSTANCE = new ApplicationValidator();

    @Override
    public ValidationResult<InputError> validate(ApplicationDto dto) {
        ValidationResult<InputError> validationResult = new ValidationResult<>();
        if (!dto.getFullName().matches("(^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)? [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?)$")) {
            validationResult.add(
                    InputError.of("invalid.fullname", "Fullname is invalid")
            );
        }
        if (!dto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$")) {
            validationResult.add(
                    InputError.of("invalid.email", "Email is invalid")
            );
        }
        if (!dto.getPhone().matches("^((\\+7|7|8)+([0-9]){10})$")) {
            validationResult.add(
                    InputError.of("invalid.phone", "Phone is invalid")
            );
        }
        if (!LocalDateFormatter.isValid(dto.getBirthday())) {
            validationResult.add(
                    InputError.of("invalid.birthday", "Birthday is invalid")
            );
        }
        if (Gender.find(dto.getGender()).isEmpty()) {
            validationResult.add(
                    InputError.of("invalid.gender", "Gender is invalid")
            );
        }
        if (!dto.getProgrammingLanguages().stream()
                .allMatch(language -> ProgrammingLanguage.find(language).isPresent())) {
            validationResult.add(
                    InputError.of("invalid.programmingLanguage", "Programming language is invalid")
            );
        }

        return validationResult;
    }

    public static ApplicationValidator getInstance() {
        return INSTANCE;
    }
}