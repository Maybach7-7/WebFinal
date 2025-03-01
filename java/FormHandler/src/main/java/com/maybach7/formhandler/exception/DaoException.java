package com.maybach7.formhandler.exception;

import java.sql.SQLException;

public class DaoException extends RuntimeException {

    public DaoException(SQLException exc) {
        super(exc);
    }
}
