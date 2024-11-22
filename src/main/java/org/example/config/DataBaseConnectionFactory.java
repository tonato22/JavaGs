package org.example.config;

import java.sql.SQLException;

public final class DataBaseConnectionFactory  {
    private DataBaseConnectionFactory() {
        throw new UnsupportedOperationException();
    }

    public static DatabaseConnection create() throws SQLException {
        return DataBaseConnectionImpl.getInstance();
    }
}
