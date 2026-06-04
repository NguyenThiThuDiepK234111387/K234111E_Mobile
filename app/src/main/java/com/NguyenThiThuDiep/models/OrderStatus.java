package com.NguyenThiThuDiep.models;

public enum OrderStatus {
    ALL("tất cả các loại hóa đơn"),
    COMPLETED("Các hóa đơn đã hoàn tất hành trình"),
    NOT_YET_PAYMENT("Hóa đơn chưa thanh toán"),
    GOING_LOGISTIC ("Hóa đang xử lý Logistic"),
    CUSTOMER_COMPLAIN ("Hóa đơn bị Khách hàng la lối um xùm");
    private String description;
    private OrderStatus(String description)
    {
        this.description=description;
    }
    public String getDescription()
    {
        return this.description;
    }
}
