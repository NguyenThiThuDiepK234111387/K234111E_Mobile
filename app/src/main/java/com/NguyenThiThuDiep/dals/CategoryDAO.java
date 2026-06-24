package com.NguyenThiThuDiep.dals;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.NguyenThiThuDiep.models.Category;

import java.util.ArrayList;

public class CategoryDAO {
    public static final String DATABASE_NAME = "K234111ESale.sqlite"; //tên bản sửa cho đúng
    public static final String TABLE_NAME = "Category"; //đây là cái mình lấy từ bảng category
    public static SQLiteDatabase database = null;

    public static ArrayList<Category> getCategories(Context context)
    {
        ArrayList<Category> categories = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME,
                null);
        while(cursor.moveToNext()) {
            String CategoryId = cursor.getString(0);
            String CategoryName = cursor.getString(1);
            Category c = new Category(CategoryId, CategoryName);
            categories.add(c);
        }
            cursor.close();
            return categories;
    }
    public static long insertCategory(Context context, Category category)
    {
        database = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        ContentValues values = new ContentValues();
        values.put("CategoryId",category.getCategoryId());
        values.put("CategoryName",category.getCategoryName());
        long result = database.insert(TABLE_NAME,null,values);
        return result;
    }
    public static long deleteCategory(Context context, Category category)
    {
        database = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        long result = database.delete(TABLE_NAME,"CategoryId=?",
                new String[]{category.getCategoryId()});
        return result;
    }
}
