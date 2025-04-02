package com.maybach7.formhandler.service;

import com.maybach7.formhandler.dao.SessionDao;
import com.maybach7.formhandler.dao.UserDao;
import com.maybach7.formhandler.entity.Session;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.DaoException;
import com.maybach7.formhandler.exception.InvalidSessionException;
import com.maybach7.formhandler.exception.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {

    private static final SessionService INSTANCE = new SessionService();
    private final SessionDao sessionDao = SessionDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    public User getUserBySessionId(String sessionId) throws InvalidSessionException {
            Session session = sessionDao.findBySessionId(sessionId);
            if(session == null) {
                throw new InvalidSessionException();
            }
            Optional<User> user = userDao.findById(session.getUserId());
            return user.get();
    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}