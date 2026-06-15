package com.hospital;

import com.hospital.model.Room;
import com.hospital.dao.RoomDAO;
import com.hospital.util.DatabaseInitializer;
import com.hospital.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test class for database functionality
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        // Test database connection
        System.out.println("Testing database connection...");
        try {
            Connection conn = DatabaseUtil.getConnection();
            System.out.println("Database connection successful!");
            DatabaseUtil.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        }
        
        // Initialize database
        System.out.println("\nInitializing database...");
        boolean initialized = DatabaseInitializer.initializeDatabase();
        if (!initialized) {
            System.err.println("Database initialization failed!");
            System.exit(1);
        }
        
        // Test RoomDAO
        System.out.println("\nTesting RoomDAO...");
        RoomDAO roomDAO = new RoomDAO();
        
        // Add a test room
        System.out.println("Adding test room...");
        Room testRoom = new Room("TEST1", "Single", 1000.0, true);
        boolean added = roomDAO.addRoom(testRoom);
        System.out.println("Room added: " + added);
        
        // Get the room
        System.out.println("Getting test room...");
        Room retrievedRoom = roomDAO.getRoomByNumber("TEST1");
        System.out.println("Retrieved room: " + retrievedRoom);
        
        // Update the room
        System.out.println("Updating test room...");
        retrievedRoom.setPrice(1500.0);
        retrievedRoom.setAvailable(false);
        boolean updated = roomDAO.updateRoom(retrievedRoom);
        System.out.println("Room updated: " + updated);
        
        // Get all rooms
        System.out.println("Getting all rooms...");
        System.out.println("Total rooms: " + roomDAO.getAllRooms().size());
        
        // Delete the test room
        System.out.println("Deleting test room...");
        boolean deleted = roomDAO.deleteRoom("TEST1");
        System.out.println("Room deleted: " + deleted);
        
        System.out.println("\nDatabase test completed successfully!");
    }
} 