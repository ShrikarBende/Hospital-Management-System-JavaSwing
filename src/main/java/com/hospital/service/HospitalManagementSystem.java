package com.hospital.service;

import com.hospital.model.*;
import java.util.*;
import com.hospital.dao.RoomDAO;
import com.hospital.util.DatabaseInitializer;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import com.hospital.dao.PatientDAO;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.ProcessBuilder;
import java.lang.Process;

public class HospitalManagementSystem {
    private List<Patient> patients;
    private List<Room> rooms;
    private List<Department> departments;
    private List<Employee> employees;
    private List<Ambulance> ambulances;
    private static final String DEFAULT_USERNAME = "shrikar";
    private static final String DEFAULT_PASSWORD = "431001";
    private RoomDAO roomDAO;
    private PatientDAO patientDAO;

    public HospitalManagementSystem() {
        // Initialize database
        DatabaseInitializer.initializeDatabase();
        
        // Initialize DAOs
        roomDAO = new RoomDAO();
        patientDAO = new PatientDAO();
        
        // Load data from database
        rooms = roomDAO.getAllRooms();
        patients = patientDAO.getAllPatients();
        
        // If no rooms exist, initialize with default data
        if (rooms.isEmpty()) {
            initializeDefaultRooms();
        }
        
        departments = new ArrayList<>();
        employees = new ArrayList<>();
        ambulances = new ArrayList<>();
        initializeData();
    }

    private void initializeData() {
        // Initialize departments
        departments.add(new Department("OPD", "1234567890"));
        departments.add(new Department("OT", "1234567891"));
        departments.add(new Department("Nursing Department", "1234567892"));
        departments.add(new Department("Surgical Department", "1234567893"));

        // Initialize some rooms
        // Note: We're using the database now, so we don't need to add rooms here
        // The rooms will be loaded from the database in the constructor

        // Initialize doctors
        employees.add(new Employee("D001", "Dr. Rajesh Kumar", 45, "Male", 150000.0, "9876543210", "rajesh.kumar@hospital.com", "Cardiologist"));
        employees.add(new Employee("D002", "Dr. Priya Sharma", 38, "Female", 140000.0, "9876543211", "priya.sharma@hospital.com", "Neurologist"));
        employees.add(new Employee("D003", "Dr. Amit Patel", 42, "Male", 145000.0, "9876543212", "amit.patel@hospital.com", "Orthopedic"));
        employees.add(new Employee("D004", "Dr. Sneha Gupta", 35, "Female", 135000.0, "9876543213", "sneha.gupta@hospital.com", "Pediatrician"));
        employees.add(new Employee("D005", "Dr. Vikram Singh", 50, "Male", 130000.0, "9876543214", "vikram.singh@hospital.com", "General Physician"));

        // Initialize nurses
        employees.add(new Employee("N001", "Sarah Wilson", 32, "Female", 80000.0, "9876543215", "sarah.wilson@hospital.com", "Head Nurse"));
        employees.add(new Employee("N002", "John Smith", 35, "Male", 75000.0, "9876543216", "john.smith@hospital.com", "Emergency Nurse"));
        employees.add(new Employee("N003", "Maria Garcia", 28, "Female", 70000.0, "9876543217", "maria.garcia@hospital.com", "ICU Nurse"));
        employees.add(new Employee("N004", "David Chen", 30, "Male", 72000.0, "9876543218", "david.chen@hospital.com", "Surgical Nurse"));
        employees.add(new Employee("N005", "Lisa Anderson", 33, "Female", 68000.0, "9876543219", "lisa.anderson@hospital.com", "Pediatric Nurse"));

        // Initialize some ambulances
        ambulances.add(new Ambulance("AMB001", "Harsh Sathe", "9876543210", "MH12AB1234"));
        ambulances.add(new Ambulance("AMB002", "Shardul Walunj", "9876543211", "MH12CD5678"));
        ambulances.add(new Ambulance("AMB003", "Savit Pandita", "9876543212", "MH12EF9012"));
        ambulances.add(new Ambulance("AMB004", "Shrikar Bende", "9876543213", "MH12GH3456"));
        ambulances.add(new Ambulance("AMB005", "Rajshekhar Shinde", "9876543214", "MH12IJ7890"));
        
        // Set AMB003 as on duty (not available)
        ambulances.get(2).setAvailable(false);
    }

    private void initializeDefaultRooms() {
        // Create default rooms
        rooms = new ArrayList<>();
        
        // Single Rooms
        for (int i = 1; i <= 10; i++) {
            Room room = new Room("S" + i, "Single", 1500.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // Double Rooms
        for (int i = 1; i <= 10; i++) {
            Room room = new Room("D" + i, "Double", 2500.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // Suite Rooms
        for (int i = 1; i <= 5; i++) {
            Room room = new Room("SU" + i, "Suite", 5000.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // ICU Rooms
        for (int i = 1; i <= 5; i++) {
            Room room = new Room("ICU" + i, "ICU", 8000.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // Deluxe Rooms
        for (int i = 1; i <= 3; i++) {
            Room room = new Room("DL" + i, "Deluxe", 3500.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // General Ward Rooms
        for (int i = 1; i <= 15; i++) {
            Room room = new Room("GW" + i, "General Ward", 800.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // Pediatric Rooms
        for (int i = 1; i <= 8; i++) {
            Room room = new Room("P" + i, "Pediatric", 2000.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
        
        // Maternity Rooms
        for (int i = 1; i <= 6; i++) {
            Room room = new Room("M" + i, "Maternity", 3000.0, true);
            rooms.add(room);
            roomDAO.addRoom(room);
        }
    }

    public boolean login(String username, String password) {
        return username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD);
    }
    // Patient Management
    public void addPatient(Patient patient) {
        boolean added = patientDAO.addPatient(patient);
        if (added) {
            patients.add(patient);
        } else {
            System.err.println(" Patient NOT added to the database!");
        }
    }
    

    public List<Patient> getAllPatients() {
        patients = patientDAO.getAllPatients();
        return new ArrayList<>(patients);
    }

    public Patient findPatientById(String patientId) {
        return patientDAO.getPatientById(patientId);
    }

    public void updatePatient(Patient patient) {
        boolean updated = patientDAO.updatePatient(patient);
        if (updated) {
            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getPatientId().equals(patient.getPatientId())) {
                    patients.set(i, patient);
                    break;
                }
            }
        }
    }

    // Room Management
    public List<Room> getAllRooms() {
        // Always get fresh data from database
        return roomDAO.getAllRooms();
    }

    public List<Room> getAvailableRooms() {
        // Get fresh data and filter available rooms
        return getAllRooms().stream()
                .filter(Room::isAvailable)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Room> getRoomsByBedType(String bedType) {
        // Get fresh data and filter by bed type
        return getAllRooms().stream()
                .filter(r -> r.getBedType().equals(bedType))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Find a room by its room number
     * @param roomNumber The room number to search for
     * @return The Room object if found, null otherwise
     */
    public Room findRoomByNumber(String roomNumber) {
        return roomDAO.getRoomByNumber(roomNumber);
    }

    /**
     * Update an existing room in the system
     * @param updatedRoom The room with updated information
     * @return true if the room was updated successfully, false otherwise
     */
    public boolean updateRoom(Room updatedRoom) {
        boolean success = roomDAO.updateRoom(updatedRoom);
        if (success) {
            // Update local list
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoomNumber().equals(updatedRoom.getRoomNumber())) {
                    rooms.set(i, updatedRoom);
                    break;
                }
            }
        }
        return success;
    }

    /**
     * Add a new room to the system
     * @param room The room to add
     * @return true if the room was added successfully, false otherwise
     */
    public boolean addRoom(Room room) {
        boolean success = roomDAO.addRoom(room);
        if (success) {
            rooms.add(room);
        }
        return success;
    }

    /**
     * Delete a room from the system
     * @param roomNumber The room number to delete
     * @return true if the room was deleted successfully, false otherwise
     */
    public boolean deleteRoom(String roomNumber) {
        boolean success = roomDAO.deleteRoom(roomNumber);
        if (success) {
            rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
        }
        return success;
    }

    // Department Management
    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    // Employee Management
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    // Ambulance Management
    public List<Ambulance> getAllAmbulances() {
        return new ArrayList<>(ambulances);
    }

    public List<Ambulance> getAvailableAmbulances() {
        return ambulances.stream()
                .filter(Ambulance::isAvailable)
                .collect(java.util.stream.Collectors.toList());
    }

    public boolean bookAmbulance(String ambulanceId) {
        return ambulances.stream()
                .filter(a -> a.getAmbulanceId().equals(ambulanceId))
                .findFirst()
                .map(a -> {
                    if (a.isAvailable()) {
                        a.setAvailable(false);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    public void releaseAmbulance(String ambulanceId) {
        ambulances.stream()
                .filter(a -> a.getAmbulanceId().equals(ambulanceId))
                .findFirst()
                .ifPresent(a -> a.setAvailable(true));
    }

    public void exportPatientsToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.write("ID,Name,Gender,Disease,Admission Date,Room Number\n");
            
            // Get all patients
            List<Patient> patients = patientDAO.getAllPatients();
            
            // Write data
            for (Patient patient : patients) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                    patient.getPatientId(),
                    patient.getName(),
                    patient.getGender(),
                    patient.getDisease(),
                    patient.getAdmissionTime().toString(),
                    patient.getRoomNumber()));
            }
            
            System.out.println("Patient data exported to " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting patient data: " + e.getMessage());
        }
    }

    public void backupDatabase(String backupPath) {
        try {
            // Create a backup directory if it doesn't exist
            File backupDir = new File(backupPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            
            // Get current date and time for the backup file name
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String backupFile = backupPath + "/hospital_db_backup_" + timestamp + ".sql";
            
            // Run mysqldump command
            ProcessBuilder pb = new ProcessBuilder(
                "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe",
                "-u", "root",
                "-pShrikar@2005",
                "hospital_db"
            );
            pb.redirectOutput(new File(backupFile));
            Process p = pb.start();
            p.waitFor();
            
            System.out.println("Database backup created at: " + backupFile);
        } catch (Exception e) {
            System.err.println("Error creating database backup: " + e.getMessage());
        }
    }

    public boolean validatePatient(Patient patient) {
        // Check if patient ID is empty
        if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
            System.err.println("Patient ID cannot be empty");
            return false;
        }
        
        // Check if patient name is empty
        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            System.err.println("Patient name cannot be empty");
            return false;
        }
        
        // Check if deposit amount is negative
        if (patient.getDepositAmount() < 0) {
            System.err.println("Deposit amount cannot be negative");
            return false;
        }
        
        // Check if room number is valid (if provided)
        if (patient.getRoomNumber() != null && !patient.getRoomNumber().trim().isEmpty()) {
            Room room = roomDAO.getRoomByNumber(patient.getRoomNumber());
            if (room == null) {
                System.err.println("Invalid room number: " + patient.getRoomNumber());
                return false;
            }
            if (!room.isAvailable()) {
                System.err.println("Room is not available: " + patient.getRoomNumber());
                return false;
            }
        }
        
        return true;
    }
} 