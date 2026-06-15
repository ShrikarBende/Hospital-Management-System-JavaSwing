package com.hospital.model;

import java.time.LocalDateTime;

public class Patient {
    private String patientId;
    private String idType; // Aadhar Card or Voter ID
    private String idNumber;
    private String name;
    private String gender;
    private String disease;
    private LocalDateTime admissionTime;
    private double depositAmount;
    private String roomNumber;
    private double pendingAmount;
    private double amountPaid;
    private LocalDateTime dischargeDate;

    public Patient(String patientId, String idType, String idNumber, String name, String gender,
                  String disease, LocalDateTime admissionTime, double depositAmount) {
        this.patientId = patientId;
        this.idType = idType;
        this.idNumber = idNumber;
        this.name = name;
        this.gender = gender;
        this.disease = disease;
        this.admissionTime = admissionTime;
        this.depositAmount = depositAmount;
        this.pendingAmount = depositAmount;
        this.amountPaid = 0.0;
    }

    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    
    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }
    
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    
    public LocalDateTime getAdmissionTime() { return admissionTime; }
    public void setAdmissionTime(LocalDateTime admissionTime) { this.admissionTime = admissionTime; }
    
    public double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(double depositAmount) { this.depositAmount = depositAmount; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public double getPendingAmount() { return pendingAmount; }
    public void setPendingAmount(double pendingAmount) { this.pendingAmount = pendingAmount; }
    
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    
    public LocalDateTime getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDateTime dischargeDate) { this.dischargeDate = dischargeDate; }
} 