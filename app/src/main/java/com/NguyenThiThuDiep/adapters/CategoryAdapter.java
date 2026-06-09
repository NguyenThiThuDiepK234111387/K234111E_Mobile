package com.NguyenThiThuDiep.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.NguyenThiThuDiep.k234111e_mobile.R;
import com.NguyenThiThuDiep.models.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resourse;
    public CategoryAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resourse=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View custom=inflater.inflate(resourse,null);
        Category c=getItem(position);
        TextView txtCategoryId=custom.findViewById(R.id.txtCategoryId);
        TextView txtCategoryName=custom.findViewById(R.id.txtCategoryName);
        txtCategoryId.setText(c.getCategoryId());
        txtCategoryName.setText(c.getCategoryName());
        return custom;
        //lưu ý phải có return
    }
}
