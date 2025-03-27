package com.maybach7.formhandler.dao;

import com.maybach7.formhandler.entity.Language;
import com.maybach7.formhandler.entity.User;
import com.maybach7.formhandler.exception.DaoException;
import com.maybach7.formhandler.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.sql.Statement;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private final static UserDao INSTANCE = new UserDao();

    private final static String SAVE_SQL =
            "INSERT INTO users(fullname, email, phone, birthday, gender, biography)" +
            "VALUES(?, ?, ?, ?, ?, ?)";

    private final static String SAVE_LANGUAGE_SQL =
            "INSERT INTO users_languages(user_id, language_id) VALUES(?, ?)";

    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("Connected to a database");
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setObject(4, user.getBirthday());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setString(6, user.getBiography());

            int insertedRows = preparedStatement.executeUpdate();
            System.out.println("Statement has inserted: " + insertedRows + " rows");

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt("id"));
            }
            return user;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    public boolean saveLanguage(User user, Language language) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_LANGUAGE_SQL)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, language.getId());

            int insertedRows = preparedStatement.executeUpdate();
            System.out.println("Statement has inserted: " + insertedRows + " rows");
            return true;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
