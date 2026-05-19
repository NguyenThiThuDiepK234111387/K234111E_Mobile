package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText edtId,edtName,edtPhone;
    AutoCompleteTextView actBirthplace;
    ArrayAdapter<String>adapterBirthplace;
    ImageView imgSave,imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {

    }

    private void addViews() {
        edtId=findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtPhone);
        actBirthplace=findViewById(R.id.actBirthplace);
        adapterBirthplace=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        String []arr_birthplace=getResources().getStringArray(R.array.list_birthplace);
        adapterBirthplace.addAll(arr_birthplace);
        actBirthplace.setAdapter(adapterBirthplace);

        imgSave=findViewById(R.id.imgSave);
        imgCancel=findViewById(R.id.imgCancel);
    }
}