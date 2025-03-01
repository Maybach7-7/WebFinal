package com.maybach7.formhandler.service;

import com.maybach7.formhandler.dao.LanguageDao;
import com.maybach7.formhandler.dao.UserDao;
import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Language;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.ValidationException;
import com.maybach7.formhandler.mapper.UserMapper;
import com.maybach7.formhandler.validator.ApplicationValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ApplicationService {

    private final static ApplicationService INSTANCE = new ApplicationService();
    private final ApplicationValidator applicationValidator = ApplicationValidator.getInstance();
    private final UserMapper  userMapper = UserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    public User createUser(ApplicationDto dto) {
        var validationResult = applicationValidator.validate(dto);
        if(!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        //  Валидация успешно закончена

        User user = userMapper.mapFrom(dto);
        user = userDao.save(user);
        for(var lang: user.getLanguages()) {
            Language language = LanguageDao.getInstance().findByName(lang.getName());
            UserDao.getInstance().saveLanguage(user, language);
        }

        return user;
    }

    public static ApplicationService getInstance() {
        return INSTANCE;
    }
}