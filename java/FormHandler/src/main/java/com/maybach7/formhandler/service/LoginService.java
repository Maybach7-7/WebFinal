package com.maybach7.formhandler.service;

import com.maybach7.formhandler.dao.CredentialsDao;
import com.maybach7.formhandler.dao.SessionDao;
import com.maybach7.formhandler.dto.LoginDto;
import com.maybach7.formhandler.entity.Credentials;
import com.maybach7.formhandler.entity.Session;
import com.maybach7.formhandler.exception.LoginException;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.validator.LoginValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginService {

    private static LoginService loginService = new LoginService();
    private final LoginValidator loginValidator = LoginValidator.getInstance();
    private final CredentialsDao credentialsDao = CredentialsDao.getInstance();
    private final SessionDao sessionDao = SessionDao.getInstance();

    public Session login(LoginDto dto) throws ValidationException, LoginException {
        var validationResult = loginValidator.validate(dto);
        if(!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        try {
            Credentials credentials = credentialsDao.findByLogin(dto.getLogin());   // достаём из базы

            var byteHash = HashingService.getHash(dto.getPassword(), HashingService.fromString(credentials.getSalt())); // хэшируем передаваемый пароль
            var hashedPassword = HashingService.toString(byteHash);
            if (!credentials.getPassword().equals(hashedPassword)) {
                throw new LoginException();
            }

            //  авторизация прошла успешно
            // создаём экземпляр сессии
            Session newSession = new Session(credentials.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusDays(7));
            // сохранить сессию на неделю в базу
            sessionDao.save(newSession);
            return newSession;
        } catch(LoginException exc) {
            throw new LoginException();
        }
    }

    public static LoginService getInstance() {
        return loginService;
    }
}
