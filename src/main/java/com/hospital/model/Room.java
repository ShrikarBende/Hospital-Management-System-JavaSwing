package com.hospital.model;

/**
 * Model class for Room entity
 */
public class Room {
    private String roomNumber;
    private String bedType;
    private double price;
    private boolean available;
    
    /**
     * Default constructor
     */
    public Room() {
    }
    
    /**
     * Constructor with all fields
     * @param roomNumber Room number
     * @param bedType Bed type (Single, Double, Suite)
     * @param price Room price
     * @param available Whether the room is available
     */
    public Room(String roomNumber, String bedType, double price, boolean available) {
        this.roomNumber = roomNumber;
        this.bedType = bedType;
        this.price = price;
        this.available = available;
    }
    
    /**
     * Constructor for backward compatibility
     * @param roomNumber Room number
     * @param price Room price
     * @param bedType Bed type (Single, Double, Suite)
     */
    public Room(String roomNumber, double price, String bedType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.bedType = bedType;
        this.available = true; // Default to available
    }
    
    /**
     * Get room number
     * @return Room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }
    
    /**
     * Set room number
     * @param roomNumber Room number
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    /**
     * Get bed type
     * @return Bed type
     */
    public String getBedType() {
        return bedType;
    }
    
    /**
     * Set bed type
     * @param bedType Bed type
     */
    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
    
    /**
     * Get room price
     * @return Room price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Set room price
     * @param price Room price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Check if room is available
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }
    
    /**
     * Set room availability
     * @param available Whether the room is available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", bedType='" + bedType + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
} 