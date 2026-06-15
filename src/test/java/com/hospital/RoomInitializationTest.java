package com.hospital;

import com.hospital.service.HospitalManagementSystem;
import com.hospital.model.Room;
import com.hospital.dao.RoomDAO;
import java.util.List;

public class RoomInitializationTest {
    public static void main(String[] args) {
        System.out.println("Initializing Rooms...");
        
        HospitalManagementSystem system = new HospitalManagementSystem();
        RoomDAO roomDAO = new RoomDAO();
        
        // Get current rooms
        List<Room> currentRooms = roomDAO.getAllRooms();
        System.out.println("Current number of rooms: " + currentRooms.size());
        
        // If no rooms exist, add default rooms
        if (currentRooms.isEmpty()) {
            System.out.println("No rooms found. Adding default rooms...");
            
            // Single Rooms
            for (int i = 1; i <= 10; i++) {
                Room room = new Room("S" + i, "Single", 1500.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Single Room: S" + i);
            }
            
            // Double Rooms
            for (int i = 1; i <= 10; i++) {
                Room room = new Room("D" + i, "Double", 2500.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Double Room: D" + i);
            }
            
            // Suite Rooms
            for (int i = 1; i <= 5; i++) {
                Room room = new Room("SU" + i, "Suite", 5000.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Suite Room: SU" + i);
            }
            
            // ICU Rooms
            for (int i = 1; i <= 5; i++) {
                Room room = new Room("ICU" + i, "ICU", 8000.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added ICU Room: ICU" + i);
            }
            
            // Deluxe Rooms
            for (int i = 1; i <= 3; i++) {
                Room room = new Room("DL" + i, "Deluxe", 3500.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Deluxe Room: DL" + i);
            }
            
            // General Ward Rooms
            for (int i = 1; i <= 15; i++) {
                Room room = new Room("GW" + i, "General Ward", 800.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added General Ward Room: GW" + i);
            }
            
            // Pediatric Rooms
            for (int i = 1; i <= 8; i++) {
                Room room = new Room("P" + i, "Pediatric", 2000.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Pediatric Room: P" + i);
            }
            
            // Maternity Rooms
            for (int i = 1; i <= 6; i++) {
                Room room = new Room("M" + i, "Maternity", 3000.0, true);
                roomDAO.addRoom(room);
                System.out.println("Added Maternity Room: M" + i);
            }
            
            System.out.println("\nAll default rooms have been added successfully!");
        } else {
            System.out.println("Rooms already exist in the database.");
        }
        
        // Display final room count
        List<Room> finalRooms = roomDAO.getAllRooms();
        System.out.println("\nFinal number of rooms: " + finalRooms.size());
        
        // Display room summary
        System.out.println("\nRoom Summary:");
        System.out.println("Single Rooms: " + countRoomsByType(finalRooms, "Single"));
        System.out.println("Double Rooms: " + countRoomsByType(finalRooms, "Double"));
        System.out.println("Suite Rooms: " + countRoomsByType(finalRooms, "Suite"));
        System.out.println("ICU Rooms: " + countRoomsByType(finalRooms, "ICU"));
        System.out.println("Deluxe Rooms: " + countRoomsByType(finalRooms, "Deluxe"));
        System.out.println("General Ward Rooms: " + countRoomsByType(finalRooms, "General Ward"));
        System.out.println("Pediatric Rooms: " + countRoomsByType(finalRooms, "Pediatric"));
        System.out.println("Maternity Rooms: " + countRoomsByType(finalRooms, "Maternity"));
    }
    
    private static long countRoomsByType(List<Room> rooms, String bedType) {
        return rooms.stream()
            .filter(room -> room.getBedType().equals(bedType))
            .count();
    }
} 