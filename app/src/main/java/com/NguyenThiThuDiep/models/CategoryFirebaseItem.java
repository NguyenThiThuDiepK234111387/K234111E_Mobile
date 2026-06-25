package com.NguyenThiThuDiep.models;

import java.io.Serializable;

public class CategoryFirebaseItem implements Serializable {
    private String categoryId;
    private String categoryName;

    public CategoryFirebaseItem() {
    }

    public CategoryFirebaseItem(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
