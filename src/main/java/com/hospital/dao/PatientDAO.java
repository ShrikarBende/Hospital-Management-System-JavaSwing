package com.hospital.dao;

import com.hospital.model.Patient;
import com.hospital.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Patient entity
 */
public class PatientDAO {
    
    /**
     * Get all patients from the database
     * @return List of patients
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM patients";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("id"),
                    rs.getString("id_type"),
                    rs.getString("id_number"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("disease"),
                    rs.getTimestamp("admission_time").toLocalDateTime(),
                    rs.getDouble("deposit_amount")
                );
                
                // Set additional fields
                patient.setRoomNumber(rs.getString("room_number"));
                patient.setPendingAmount(rs.getDouble("pending_amount"));
                patient.setAmountPaid(rs.getDouble("amount_paid"));
                
                if (rs.getTimestamp("discharge_date") != null) {
                    patient.setDischargeDate(rs.getTimestamp("discharge_date").toLocalDateTime());
                }
                
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.err.println("Error getting patients: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseUtil.closeConnection(conn);
        }
        
        return patients;
    }
    
    /**
     * Get a patient by ID
     * @param patientId Patient ID to search for
     * @return Patient object if found, null otherwise
     */
    public Patient getPatientById(String patientId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM patients WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, patientId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("id"),
                    rs.getString("id_type"),
                    rs.getString("id_number"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("disease"),
                    rs.getTimestamp("admission_time").toLocalDateTime(),
                    rs.getDouble("deposit_amount")
                );
                
                // Set additional fields
                patient.setRoomNumber(rs.getString("room_number"));
                patient.setPendingAmount(rs.getDouble("pending_amount"));
                patient.setAmountPaid(rs.getDouble("amount_paid"));
                
                if (rs.getTimestamp("discharge_date") != null) {
                    patient.setDischargeDate(rs.getTimestamp("discharge_date").toLocalDateTime());
                }
                
                return patient;
            }
        } catch (SQLException e) {
            System.err.println("Error getting patient: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
            DatabaseUtil.closeConnection(conn);
        }
        
        return null;
    }
    
    /**
     * Add a patient to the database
     * @param patient Patient to add
     * @return true if successful, false otherwise
     */
    public boolean addPatient(Patient patient) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "INSERT INTO patients (id, id_type, id_number, name, gender, disease, admission_time, deposit_amount, room_number, pending_amount, amount_paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getPatientId());
            stmt.setString(2, patient.getIdType());
            stmt.setString(3, patient.getIdNumber());
            stmt.setString(4, patient.getName());
            stmt.setString(5, patient.getGender());
            stmt.setString(6, patient.getDisease());
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(patient.getAdmissionTime()));
            stmt.setDouble(8, patient.getDepositAmount());
            stmt.setString(9, patient.getRoomNumber());
            stmt.setDouble(10, patient.getPendingAmount());
            stmt.setDouble(11, patient.getAmountPaid());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
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
     * Update a patient in the database
     * @param patient Patient to update
     * @return true if successful, false otherwise
     */
    public boolean updatePatient(Patient patient) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "UPDATE patients SET id_type = ?, id_number = ?, name = ?, gender = ?, disease = ?, admission_time = ?, deposit_amount = ?, room_number = ?, pending_amount = ?, amount_paid = ?, discharge_date = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getIdType());
            stmt.setString(2, patient.getIdNumber());
            stmt.setString(3, patient.getName());
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getDisease());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(patient.getAdmissionTime()));
            stmt.setDouble(7, patient.getDepositAmount());
            stmt.setString(8, patient.getRoomNumber());
            stmt.setDouble(9, patient.getPendingAmount());
            stmt.setDouble(10, patient.getAmountPaid());
            stmt.setTimestamp(11, patient.getDischargeDate() != null ? java.sql.Timestamp.valueOf(patient.getDischargeDate()) : null);
            stmt.setString(12, patient.getPatientId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
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
     * Delete a patient from the database
     * @param patientId ID of the patient to delete
     * @return true if successful, false otherwise
     */
    public boolean deletePatient(String patientId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "DELETE FROM patients WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, patientId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
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