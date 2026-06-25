package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class ProductFirebaseItem implements Serializable {
    private String productId;
    private String productName;
    private double price;
    private int stock; // Đổi từ quantity sang stock theo Firebase
    private double coupon;
    private double VAT;
    private String categoryId;
    private String imageUrl; // Đổi từ image sang imageUrl theo Firebase

    public ProductFirebaseItem() {
    }

    public ProductFirebaseItem(String productId, String productName, double price, int stock, double coupon, double VAT, String categoryId, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.coupon = coupon;
        this.VAT = VAT;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Giữ lại getter cũ để không làm hỏng code hiện tại nếu cần, hoặc cập nhật adapter
    public String getImage() { return imageUrl; }
    public int getQuantity() { return stock; }
}
