package com.maybach7.formhandler.validator;

import com.maybach7.formhandler.dto.LoginDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginValidator implements Validator<LoginDto, InputError> {

    private static final LoginValidator INSTANCE = new LoginValidator();

    @Override
    public ValidationResult<InputError> validate(LoginDto dto) {
        ValidationResult<InputError> validationResult = new ValidationResult<>();
        if(!dto.getLogin().matches("^[a-z]{10}$")) {
            validationResult.add(
                    InputError.of("login", "Логин должен состоять из 10 английских букв")
            );
        }

        return validationResult;
    }

    public static LoginValidator getInstance() {
        return INSTANCE;
    }
}
