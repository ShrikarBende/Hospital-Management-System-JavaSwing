package com.hospital;

import com.hospital.service.HospitalManagementSystem;
import com.hospital.model.Patient;
import java.time.LocalDateTime;

public class PatientTest {
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
        
        // Verify the patient was added
        Patient retrievedPatient = system.findPatientById("P01");
        if (retrievedPatient != null) {
            System.out.println("Patient added successfully!");
            System.out.println("Name: " + retrievedPatient.getName());
            System.out.println("ID: " + retrievedPatient.getPatientId());
            System.out.println("Disease: " + retrievedPatient.getDisease());
        } else {
            System.out.println("Failed to add patient!");
        }
    }
} 