package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility class for managing database connections
 */
public class DatabaseUtil {
    private static final String CONFIG_FILE = "database.properties";
    private static String url;
    private static String username;
    private static String password;
    
    static {
        try {
            // Load database configuration
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
                props.load(fis);
            } catch (IOException e) {
                // If file doesn't exist, use default values
                System.out.println("Database configuration file not found. Using default values.");
                url = "jdbc:mysql://localhost:3306/hospital_db?createDatabaseIfNotExist=true";
                username = "root";
                password = "password";
            }
            
            // Get properties if they exist
            if (props.containsKey("db.url")) {
                url = props.getProperty("db.url");
                username = props.getProperty("db.username");
                password = props.getProperty("db.password");
            }
            
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }
    
    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Close a database connection
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
} 