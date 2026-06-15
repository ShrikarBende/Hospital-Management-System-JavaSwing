package com.hospital.model;

public class Employee {
    private String employeeId;
    private String name;
    private int age;
    private String gender;
    private double salary;
    private String phoneNumber;
    private String email;
    private String role; // Doctor or Nurse

    public Employee(String employeeId, String name, int age, String gender, 
                   double salary, String phoneNumber, String email, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 