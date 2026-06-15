package com.hospital;

import com.hospital.service.HospitalManagementSystem;
import com.hospital.model.Room;
import java.util.List;

/**
 * Test class for room management functionality
 */
public class RoomTest {
    
    public static void main(String[] args) {
        System.out.println("Testing Room Management Functionality...");
        
        // Create a new instance of HospitalManagementSystem
        HospitalManagementSystem hms = new HospitalManagementSystem();
        
        // Get all rooms
        List<Room> allRooms = hms.getAllRooms();
        System.out.println("Total rooms: " + allRooms.size());
        
        // Print all rooms
        System.out.println("\nAll Rooms:");
        for (Room room : allRooms) {
            System.out.println(room);
        }
        
        // Get available rooms
        List<Room> availableRooms = hms.getAvailableRooms();
        System.out.println("\nAvailable rooms: " + availableRooms.size());
        
        // Get rooms by bed type
        List<Room> singleRooms = hms.getRoomsByBedType("Single");
        System.out.println("Single rooms: " + singleRooms.size());
        
        List<Room> doubleRooms = hms.getRoomsByBedType("Double");
        System.out.println("Double rooms: " + doubleRooms.size());
        
        List<Room> suiteRooms = hms.getRoomsByBedType("Suite");
        System.out.println("Suite rooms: " + suiteRooms.size());
        
        // Add a new room
        Room newRoom = new Room("TEST1", "Test", 1000.0, true);
        boolean added = hms.addRoom(newRoom);
        System.out.println("\nNew room added: " + added);
        
        // Find the room by number
        Room foundRoom = hms.findRoomByNumber("TEST1");
        System.out.println("Found room: " + foundRoom);
        
        // Update the room
        foundRoom.setPrice(1500.0);
        foundRoom.setAvailable(false);
        boolean updated = hms.updateRoom(foundRoom);
        System.out.println("Room updated: " + updated);
        
        // Delete the room
        boolean deleted = hms.deleteRoom("TEST1");
        System.out.println("Room deleted: " + deleted);
        
        System.out.println("\nRoom management test completed.");
    }
} 