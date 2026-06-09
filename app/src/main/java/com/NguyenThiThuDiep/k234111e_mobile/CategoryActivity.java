package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.adapters.CategoryAdapter;
import com.NguyenThiThuDiep.dals.CategoryDAD;
import com.NguyenThiThuDiep.models.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView lvCategory;
    ArrayList<Category> categories;
    CategoryAdapter adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        addView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addView() {
        lvCategory=findViewById(R.id.lvCategory);
        categories= CategoryDAD.getCategories(this);
        adapterCategory=new CategoryAdapter(this,R.layout.category_custom_item);
        lvCategory.setAdapter(adapterCategory);
        adapterCategory.addAll(categories);
    }
}