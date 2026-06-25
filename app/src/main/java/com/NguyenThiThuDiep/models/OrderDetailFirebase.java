package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class OrderDetailFirebase implements Serializable {
    private String orderDetailId;
    private String orderId;
    private String productId;
    private int quantity;
    private double unitPrice; // Renamed from price

    public OrderDetailFirebase() {}

    public String getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(String orderDetailId) { this.orderDetailId = orderDetailId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
