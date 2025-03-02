package com.maybach7.formhandler.dao;

import com.maybach7.formhandler.entity.Language;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.exception.DaoException;
import com.maybach7.formhandler.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LanguageDao {
    private static final LanguageDao INSTANCE = new LanguageDao();

    public static final String FIND_BY_NAME =
            "SELECT * FROM languages where name = ?";

    public Language findByName(String name) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);

            var result = preparedStatement.executeQuery();
            result.next();
            System.out.println("Has been found a row rows");
            var language = buildLanguage(result);

            return language;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    private Language buildLanguage(ResultSet res) throws SQLException {
        return new Language(
                res.getInt("id"),
                ProgrammingLanguage.get(res.getString("name"))
        );
    }

    public static LanguageDao getInstance() {
        return INSTANCE;
    }
}