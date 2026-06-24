package com.NguyenThiThuDiep.dals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.NguyenThiThuDiep.models.Product;

import java.util.ArrayList;

public class ProductDAO {
    public static final String DATABASE_NAME = "K234111ESale.sqlite"; //tên bản sửa cho đúng
    public static final String TABLE_NAME = "Product"; //đây là cái mình lấy từ bảng Product
    public static SQLiteDatabase database = null;

    public static ArrayList<Product> getProducts(Context context)
    {
        ArrayList<Product> products = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME,
                null);
        while(cursor.moveToNext()) {
            String productId = cursor.getString(0);
            String productName = cursor.getString(1);
            double price = cursor.getDouble(2);
            int quantity = cursor.getInt(3);
            double coupon = cursor.getDouble(4);
            double VAT = cursor.getDouble(5);
            String categoryId = cursor.getString(6);
            Product p = new Product(productId, productName, price, quantity, coupon, VAT, categoryId);
            products.add(p);
        }
        cursor.close();
        return products;
    }

    public static long insertProduct(Context context, Product product)
    {
        database = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        ContentValues values = new ContentValues();
        values.put("ProductId",product.getProductId());
        values.put("ProductName",product.getProductName());
        values.put("Price",product.getPrice());
        values.put("Quantity",product.getQuantity());
        values.put("Coupon",product.getCoupon());
        values.put("VAT",product.getVAT());
        values.put("CategoryId",product.getCategoryId());
        long result = database.insert(TABLE_NAME,null,values);
        return result;
    }

    public static long updateProduct(Context context, Product product)
    {
        database = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        ContentValues values = new ContentValues();
        values.put("ProductName",product.getProductName());
        values.put("Price",product.getPrice());
        values.put("Quantity",product.getQuantity());
        values.put("Coupon",product.getCoupon());
        values.put("VAT",product.getVAT());
        values.put("CategoryId",product.getCategoryId());
        long result = database.update(TABLE_NAME,values,"ProductId=?",
                new String[]{product.getProductId()});
        return result;
    }

    public static long deleteProduct(Context context, Product product)
    {
        database = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        long result = database.delete(TABLE_NAME,"ProductId=?",
                new String[]{product.getProductId()});
        return result;
    }
}