package com.example.access;

public class Employee {
    private String id;
    private String name;
    private int age;
    private String department;
    private String employmentType; 
    private int securityClearanceLevel; 
    private boolean isIdValid;

    public Employee(String id, String name, int age, String department, String employmentType, int securityClearanceLevel, boolean isIdValid) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.employmentType = employmentType;
        this.securityClearanceLevel = securityClearanceLevel;
        this.isIdValid = isIdValid;
    }

    // Getters and Encapsulation Boundary
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public String getEmploymentType() { return employmentType; }
    public int getSecurityClearanceLevel() { return securityClearanceLevel; }
    public boolean isIdValid() { return isIdValid; }
}
