package com.hospital;

import com.hospital.service.HospitalManagementSystem;
import com.hospital.model.Patient;
import java.time.LocalDateTime;
import java.util.List;

public class PatientListTest {
    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem();
        
        // Create a test patient
        Patient patient = new Patient(
            "P01",
            "Aadhar Card",
            "1234-5678-9012",
            "Test Patient",
            "Male",
            "Fever",
            LocalDateTime.now(),
            5000.0
        );
        
        // Add the patient
        system.addPatient(patient);
        
        // Get all patients
        List<Patient> patients = system.getAllPatients();
        System.out.println("Total patients: " + patients.size());
        
        // Print patient details
        for (Patient p : patients) {
            System.out.println("\nPatient Details:");
            System.out.println("ID: " + p.getPatientId());
            System.out.println("Name: " + p.getName());
            System.out.println("Disease: " + p.getDisease());
            System.out.println("Room: " + (p.getRoomNumber() != null ? p.getRoomNumber() : "Not Assigned"));
        }
    }
} 