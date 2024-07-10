package edu.raddan.utils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHandler {

    private static final URL dbPath = SQLHandler.class.getClassLoader().getResource("sql/currency-exchanger.db");

    private static class SingletonHolder {
        public static final SQLHandler instance = new SQLHandler();
    }

    public static SQLHandler getInstance() {
        return SingletonHolder.instance;
    }

    private SQLHandler() { }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("SQLite JDBC driver not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (dbPath == null)
            throw new IllegalStateException("Database not found!");

        String path = new File(dbPath.getFile()).getAbsolutePath();
        String url = "jdbc:sqlite:" + path;
        return DriverManager.getConnection(url);
    }

}
