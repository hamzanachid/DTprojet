package org.example.dao.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static DatabaseConnection instance;

    private DatabaseConnection() {
        loadDatabaseConfig();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void loadDatabaseConfig() {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file config.yml not found!");
            }


            Map<String, Object> config = yaml.load(inputStream);
            Map<String, String> databaseConfig = (Map<String, String>) config.get("database");


            URL = databaseConfig.get("url");
            USER = databaseConfig.get("user");
            PASSWORD = databaseConfig.get("password");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration: " + e.getMessage());
        }
    }
}
