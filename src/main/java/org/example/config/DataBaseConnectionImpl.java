package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

final class DataBaseConnectionImpl implements DatabaseConnection {
    private static DataBaseConnectionImpl dbConnection;

    private static Connection connection;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private DataBaseConnectionImpl() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );
        } catch (ClassNotFoundException e) {
            logger.severe("o driver do oracle não foi localizado");
        }

    }

    public static synchronized DataBaseConnectionImpl getInstance() throws SQLException {
        if (dbConnection == null || connection.isClosed()) {
            dbConnection = new DataBaseConnectionImpl();
        }
        return dbConnection;
    }

    @Override
    public Connection get() throws SQLException {
        connection.setAutoCommit(false);
        return connection;
    }
}
