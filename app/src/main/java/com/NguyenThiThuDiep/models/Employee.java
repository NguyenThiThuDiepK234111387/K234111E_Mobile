package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class Employee implements Serializable { //nhớ thêm implements Serializable nhé để khỏi null
    private String employeeId;
    private String employeeName;
    private String phoneNumber;
    private String birthPlace;

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    public Employee() {
    }

    public Employee(String employeeId, String employeeName, String phoneNumber, String birthPlace) {
        this(employeeId,employeeName,phoneNumber);
        this.birthPlace = birthPlace;
    }

    public Employee(String employeeId, String employeeName, String phoneNumber) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
