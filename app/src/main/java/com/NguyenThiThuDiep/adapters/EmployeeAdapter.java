package com.NguyenThiThuDiep.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.NguyenThiThuDiep.k234111e_mobile.R;
import com.NguyenThiThuDiep.models.Employee;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    Activity context;
    int resource;
    public EmployeeAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        //nhân bản giao diện tại vị trí thứ position
        View customView=inflater.inflate(this.resource,null);
        //Lấy Employee ở vị trí position:
        Employee emp=this.getItem(position);
        //show Employee into GUI:
        TextView txtId=customView.findViewById(R.id.txtId);
        TextView txtName=customView.findViewById(R.id.txtName);
        TextView txtPhone=customView.findViewById(R.id.txtPhone);
        txtId.setText(emp.getEmployeeId());
        txtName.setText(emp.getEmployeeName());
        txtPhone.setText(emp.getPhoneNumber());

        ImageView imgCall=customView.findViewById(R.id.imgCall);
        ImageView imgSms=customView.findViewById(R.id.imgSms);
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCall=new Intent(Intent.ACTION_DIAL);
                Uri uriCall=Uri.parse("tel:"+emp.getPhoneNumber());
                intentCall.setData(uriCall);
                context.startActivity(intentCall);
            }
        });
        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return customView;//phải return customView
    }
}
