package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                processDeleteCategory(i);
                return false;
            }
        });
    }
    private void processDeleteCategory(int i) {
        Toast.makeText(this, "Delete category id", Toast.LENGTH_SHORT).show();
        Category category=categories.get(i);
        long result=CategoryDAD.deleteCategory(this,category);
        if (result>0)
        {
            categories= CategoryDAD.getCategories(this);
            adapterCategory.clear();
            adapterCategory.addAll(categories);
            adapterCategory.notifyDataSetChanged();

        }
    }

    private void addView() {
        lvCategory=findViewById(R.id.lvCategory);
        categories= CategoryDAD.getCategories(this);
        adapterCategory=new CategoryAdapter(this,R.layout.category_custom_item);
        lvCategory.setAdapter(adapterCategory);
        adapterCategory.addAll(categories);
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.category_menu,menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item){
            if (item.getItemId()==R.id.mnu_category_add_new)
            {
                startActivityForResult(new Intent(this,CategoryNewActivity.class),1);
            }
            return super.onOptionsItemSelected(item);
        }
       @Override
        protected void onActivityResult(int requestCode,int resultCode, @NonNull Intent data){

            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==1&& resultCode==2)
            {
                //LÀ USER CHỌN CANCEL -> làm thinh không làm gì hết
            }
            else if (requestCode==1&& resultCode==3)
            {
                //là user chọn yes -> ta cần cập nhập lại giao diện
                categories= CategoryDAD.getCategories(this);
                adapterCategory=new CategoryAdapter(this,R.layout.category_custom_item);
                lvCategory.setAdapter(adapterCategory);
                adapterCategory.addAll(categories);
            }
        }
    }
