package com.hospital.dao;

import com.hospital.model.Room;
import com.hospital.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Room entity
 */
public class RoomDAO {
    
    /**
     * Get all rooms from the database
     * @return List of rooms
     */
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM rooms";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Room room = new Room(
                    rs.getString("room_number"),
                    rs.getString("bed_type"),
                    rs.getDouble("price"),
                    rs.getBoolean("available")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error getting rooms: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseUtil.closeConnection(conn);
        }
        
        return rooms;
    }
    
    /**
     * Get a room by room number
     * @param roomNumber Room number to search for
     * @return Room object if found, null otherwise
     */
    public Room getRoomByNumber(String roomNumber) {
        Room room = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM rooms WHERE room_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                room = new Room(
                    rs.getString("room_number"),
                    rs.getString("bed_type"),
                    rs.getDouble("price"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting room: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseUtil.closeConnection(conn);
        }
        
        return room;
    }
    
    /**
     * Add a new room to the database
     * @param room Room to add
     * @return true if successful, false otherwise
     */
    public boolean addRoom(Room room) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "INSERT INTO rooms (room_number, bed_type, price, available) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getBedType());
            stmt.setDouble(3, room.getPrice());
            stmt.setBoolean(4, room.isAvailable());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
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
     * Update a room in the database
     * @param room Room to update
     * @return true if successful, false otherwise
     */
    public boolean updateRoom(Room room) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "UPDATE rooms SET bed_type = ?, price = ?, available = ? WHERE room_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, room.getBedType());
            stmt.setDouble(2, room.getPrice());
            stmt.setBoolean(3, room.isAvailable());
            stmt.setString(4, room.getRoomNumber());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
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
     * Delete a room from the database
     * @param roomNumber Room number to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteRoom(String roomNumber) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "DELETE FROM rooms WHERE room_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNumber);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
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
} 