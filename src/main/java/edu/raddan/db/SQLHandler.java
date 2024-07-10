package edu.raddan.db;

import edu.raddan.annotation.Table;
import edu.raddan.entity.Entity;
import org.reflections.Reflections;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SQLHandler {

    private static final URL dbPath = SQLHandler.class.getClassLoader().getResource("sql/currency-exchanger.db");

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("SQLite JDBC driver not found", e);
        }
    }

    public static List<String> collectTableNames(String packageName) {
        List<String> tableNames = new ArrayList<>();

        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Entity>> classes = reflections.getSubTypesOf(Entity.class);

        for (Class<? extends Entity> clazz : classes) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                Table table = clazz.getAnnotation(Table.class);
                if (table != null)
                    tableNames.add(table.name());
            }
        }

        return tableNames;
    }

    public static Connection getConnection() throws SQLException {
        if (dbPath == null)
            throw new IllegalStateException("Database not found!");

        String path = new File(dbPath.getFile()).getAbsolutePath();
        String url = "jdbc:sqlite:" + path;
        return DriverManager.getConnection(url);
    }

    public static void initializeTables(Connection connection) throws SQLException, IOException {
        InputStream inputStream = SQLHandler.class.getClassLoader().getResourceAsStream("sql/dbInit.sql");
        if (inputStream == null)
            throw new IllegalStateException("SQL script not found!");

        String sql = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

}
