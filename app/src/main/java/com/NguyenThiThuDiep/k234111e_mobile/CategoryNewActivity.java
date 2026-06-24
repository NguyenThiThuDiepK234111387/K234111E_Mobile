package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.dals.CategoryDAO;
import com.NguyenThiThuDiep.models.Category;

public class CategoryNewActivity extends AppCompatActivity {
    EditText edtCategoryId;
    EditText edtCategoryName;
    Button btnSave;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_new);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void addViews() {
        edtCategoryId=findViewById(R.id.edtCategoryId);
        edtCategoryName=findViewById(R.id.edtCategoryName);
    }

    public void processSaveCategory(View view) {
        Category category=new Category();
        category.setCategoryId(edtCategoryId.getText().toString());
        category.setCategoryName(edtCategoryName.getText().toString());
        long result = CategoryDAO.insertCategory(this,category);
        if (result>0)
        {
            Intent intent=getIntent();
            //assume 3 is save
            setResult(3,intent);
            finish();
        }
        else
        {
            finish();
        }
    }

    public void processCancelCategory(View view) {
        Intent intent=getIntent();
        //assume 2 is cancel
        setResult(2,intent);
        finish();
    }
}