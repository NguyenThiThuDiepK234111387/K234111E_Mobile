package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.NguyenThiThuDiep.models.CategoryFirebaseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryFirebaseActivity extends AppCompatActivity {

    ListView lvCategories;
    ArrayList<CategoryFirebaseItem> categoryList;
    ArrayAdapter<CategoryFirebaseItem> categoryAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_firebase);

        lvCategories = findViewById(R.id.lvCategories);
        categoryList = new ArrayList<>();
        
        // Customizing toString in CategoryFirebaseItem or using a custom adapter.
        // For simplicity, I'll use a custom adapter or just an anonymous override for now.
        categoryAdapter = new ArrayAdapter<CategoryFirebaseItem>(this, android.R.layout.simple_list_item_1, categoryList) {
            @NonNull
            @Override
            public android.view.View getView(int position, android.view.View convertView, @NonNull android.view.ViewGroup parent) {
                android.widget.TextView textView = (android.widget.TextView) super.getView(position, convertView, parent);
                textView.setText(categoryList.get(position).getCategoryName());
                return textView;
            }
        };
        lvCategories.setAdapter(categoryAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("categories");

        loadCategories();

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryFirebaseItem selectedCategory = categoryList.get(position);
                Intent intent = new Intent(CategoryFirebaseActivity.this, ProductFirebaseActivity.class);
                intent.putExtra("CATEGORY_ID", selectedCategory.getCategoryId());
                startActivity(intent);
            }
        });
    }

    private void loadCategories() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                // Optionally add an "All" category if the user wants default as all
                categoryList.add(new CategoryFirebaseItem("ALL", "All Products"));
                
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryFirebaseItem category = dataSnapshot.getValue(CategoryFirebaseItem.class);
                    if (category != null) {
                        // Nếu categoryId trong object null, lấy Key của node làm ID
                        if (category.getCategoryId() == null || category.getCategoryId().isEmpty()) {
                            category.setCategoryId(dataSnapshot.getKey());
                        }
                        categoryList.add(category);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
