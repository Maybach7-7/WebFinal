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
        if (!dto.getFullName().matches("(^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)? [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?)$") || dto.getFullName().length() > 150) {
            validationResult.add(
                    InputError.of("fullname", "ФИО должно состоять из 3 слов на русском языке и быть не длиннее 150 символов")
            );
        }
        if (!dto.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")) {
            validationResult.add(
                    InputError.of("email", "Почта введена неверно")
            );
        }
        if (!dto.getPhone().matches("^((\\+7|7|8)+([0-9]){10})$")) {
            validationResult.add(
                    InputError.of("phone", "Номер телефона должен иметь вид: +7/7/8 + 10 цифр")
            );
        }
        if (!LocalDateFormatter.isValid(dto.getBirthday())) {
            validationResult.add(
                    InputError.of("birthday", "Birthday is invalid")
            );
        }
        if (Gender.find(dto.getGender()).isEmpty()) {
            validationResult.add(
                    InputError.of("gender", "Gender is invalid")
            );
        }
        if (!dto.getProgrammingLanguages().stream()
                .allMatch(language -> ProgrammingLanguage.find(language).isPresent())) {
            validationResult.add(
                    InputError.of("programmingLanguage", "Programming language is invalid")
            );
        }

        return validationResult;
    }

    public static ApplicationValidator getInstance() {
        return INSTANCE;
    }
}