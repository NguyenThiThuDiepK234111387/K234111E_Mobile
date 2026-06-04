package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String phoneNumber, String email, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
//phải là 1 1 trước rồi mới làm 1 nhieu
