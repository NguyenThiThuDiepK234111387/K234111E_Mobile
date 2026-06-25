package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class OrderFirebase implements Serializable {
    private String orderId;
    private String customerId;
    private String employeeId;
    private String orderDate; // Updated to String for "2026-06-15T08:30:00Z"
    private String status;    // Renamed from orderStatus
    private double totalAmount; // Renamed from totalPrice

    public OrderFirebase() {}

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getFormattedDate() {
        if (orderDate == null) return "";
        // For simplicity, returning the raw string or you could parse ISO 8601
        return orderDate.replace("T", " ").replace("Z", "");
    }
}
