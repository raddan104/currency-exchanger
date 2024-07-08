package edu.raddan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static class DatabaseHandlerHolder {
        public static final DatabaseHandler INSTANCE = new DatabaseHandler();
    }

    public static DatabaseHandler getInstance() {
        return DatabaseHandlerHolder.INSTANCE;
    }

    private static final String DB_URL = "jdbc:postgresql:5432/currency-exchanger";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "7654";

    private Connection connection;

    private DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            System.err.println("Failed to get connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
