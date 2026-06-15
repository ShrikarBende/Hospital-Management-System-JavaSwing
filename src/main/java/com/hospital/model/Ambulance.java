package com.hospital.model;

public class Ambulance {
    private String ambulanceId;
    private boolean isAvailable;
    private String driverName;
    private String driverPhone;
    private String plateNumber;

    public Ambulance(String ambulanceId, String driverName, String driverPhone, String plateNumber) {
        this.ambulanceId = ambulanceId;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.plateNumber = plateNumber;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getAmbulanceId() { return ambulanceId; }
    public void setAmbulanceId(String ambulanceId) { this.ambulanceId = ambulanceId; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    
    public String getDriverPhone() { return driverPhone; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }
    
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
} 