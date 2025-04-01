package com.maybach7.formhandler.dao;

import com.maybach7.formhandler.entity.Credentials;
import com.maybach7.formhandler.exception.DaoException;
import com.maybach7.formhandler.exception.LoginException;
import com.maybach7.formhandler.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialsDao {
    private static final CredentialsDao INSTANCE = new CredentialsDao();

    private static final String SAVE_SQL =
            "INSERT INTO users_credentials(user_id, login, password, salt)" +
            "VALUES(?, ?, ?, ?)";

    private static final String FIND_BY_LOGIN_SQL =
            "SELECT * FROM users_credentials WHERE login = ?";

    public Credentials save(Credentials credentials) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, credentials.getUserId());
            preparedStatement.setString(2, credentials.getLogin());
            preparedStatement.setString(3, credentials.getPassword());
            preparedStatement.setString(4, credentials.getSalt());

            preparedStatement.executeUpdate();
            return credentials;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    public Credentials findByLogin(String login) throws LoginException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, login);

            var result = preparedStatement.executeQuery();
            result.next();
            var credentials = buildCredentials(result);
            return credentials;
        } catch (SQLException exc) {
            throw new LoginException();
        }
    }

    private Credentials buildCredentials(ResultSet res) throws SQLException {
        return new Credentials(
                res.getInt("user_id"),
                res.getString("login"),
                res.getString("password"),
                res.getString("salt")
        );
    }

    public static CredentialsDao getInstance() {
        return INSTANCE;
    }
}