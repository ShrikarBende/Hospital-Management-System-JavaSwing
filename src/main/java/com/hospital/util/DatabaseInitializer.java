package com.hospital.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Utility class to initialize the database schema
 */
public class DatabaseInitializer {
    
    /**
     * Initialize the database schema
     * @return true if initialization was successful, false otherwise
     */
    public static boolean initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // Get connection
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement();
            
            // Read SQL script
            String sqlScript = readSqlScript();
            
            // Execute SQL script
            for (String statement : sqlScript.split(";")) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }
            
            System.out.println("Database initialized successfully");
            return true;
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
            DatabaseUtil.closeConnection(conn);
        }
    }
    
    /**
     * Read the SQL script from resources
     * @return SQL script as a string
     */
    private static String readSqlScript() {
        try (InputStream is = DatabaseInitializer.class.getClassLoader().getResourceAsStream("database.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            System.err.println("Error reading SQL script: " + e.getMessage());
            return "";
        }
    }
} 