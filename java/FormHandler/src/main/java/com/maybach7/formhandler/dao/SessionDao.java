package com.maybach7.formhandler.dao;

import com.maybach7.formhandler.entity.Session;
import com.maybach7.formhandler.exception.DaoException;
import com.maybach7.formhandler.exception.InvalidSessionException;
import com.maybach7.formhandler.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionDao {

    private static final SessionDao INSTANCE = new SessionDao();

    private static final String SAVE_SQL =
            "INSERT INTO users_sessions(session_id, user_id, expires_at)" +
            "VALUES(?, ?, ?)";

    private static final String FIND_BY_SESSION_ID_SQL =
            "SELECT * FROM users_sessions WHERE session_id = ?";

    private static final String DELETE_BY_SESSION_ID =
            "DELETE FROM users_sessions WHERE session_id = ?";

    public Session save(Session session) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, session.getSessionId());
            preparedStatement.setInt(2, session.getUserId());
            preparedStatement.setObject(3, session.getExpiresAt());
            preparedStatement.executeUpdate();

            return session;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    public Session findBySessionId(String sessionId) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(FIND_BY_SESSION_ID_SQL)) {
            preparedStatement.setString(1, sessionId);
            var result = preparedStatement.executeQuery();

            if(!result.next()) {
                return null;
            }
            var session = buildSession(result);

            return session;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    public boolean deleteBySessionId(String sessionId) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(DELETE_BY_SESSION_ID)) {
            preparedStatement.setString(1, sessionId);
            int deletedRows = preparedStatement.executeUpdate();

            return deletedRows > 0;
        } catch (SQLException exc) {
            throw new DaoException(exc);
        }
    }

    private Session buildSession(ResultSet res) throws SQLException {
        return new Session(
                res.getString("session_id"),
                res.getInt("user_id"),
                res.getTimestamp("created_at").toLocalDateTime(),
                res.getTimestamp("expires_at").toLocalDateTime()
        );
    }

    public static SessionDao getInstance() {
        return INSTANCE;
    }
}
