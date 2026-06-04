package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private String oderDetailId;
    private String orderId;
    private String productId;
    private int quantity;
    private double price;
    private double coupon;
    private double VAT;

    public OrderDetail() {
    }

    public OrderDetail(String oderDetailId, String orderId, String productId, int quantity, double price, double coupon, double VAT) {
        this.oderDetailId = oderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.coupon = coupon;
        this.VAT = VAT;
    }

    public String getOderDetailId() {
        return oderDetailId;
    }

    public void setOderDetailId(String oderDetailId) {
        this.oderDetailId = oderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "oderDetailId='" + oderDetailId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", coupon=" + coupon +
                ", VAT=" + VAT +
                '}';
    }
}
