package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.NguyenThiThuDiep.adapters.ProductFirebaseAdapter;
import com.NguyenThiThuDiep.models.ProductFirebaseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFirebaseActivity extends AppCompatActivity {

    EditText edtSearch;
    ListView lvProducts;
    ArrayList<ProductFirebaseItem> productList;
    ArrayList<ProductFirebaseItem> filteredList;
    ProductFirebaseAdapter productAdapter;
    DatabaseReference databaseReference;
    String selectedCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_firebase);

        addControls();
        addEvents();

        selectedCategoryId = getIntent().getStringExtra("CATEGORY_ID");
        if (selectedCategoryId == null) {
            selectedCategoryId = "ALL";
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        loadProducts();
    }

    private void addControls() {
        edtSearch = findViewById(R.id.edtSearch);
        lvProducts = findViewById(R.id.lvProducts);
        productList = new ArrayList<>();
        filteredList = new ArrayList<>();
        productAdapter = new ProductFirebaseAdapter(this, R.layout.item_product_firebase, filteredList);
        lvProducts.setAdapter(productAdapter);
    }

    private void addEvents() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductFirebaseItem product = dataSnapshot.getValue(ProductFirebaseItem.class);
                    if (product != null) {
                        // Lấy categoryId từ product, nếu null thì coi như không thuộc category nào
                        String pCatId = product.getCategoryId();
                        
                        // Kiểm tra điều kiện lọc:
                        // 1. Nếu chọn "ALL" thì lấy tất cả.
                        // 2. Nếu không phải "ALL", so sánh categoryId của sản phẩm với ID được chọn.
                        if ("ALL".equals(selectedCategoryId) || (pCatId != null && pCatId.equals(selectedCategoryId))) {
                            productList.add(product);
                        }
                    }
                }
                filterProducts(edtSearch.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void filterProducts(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            for (ProductFirebaseItem item : productList) {
                if (item.getProductName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        productAdapter.notifyDataSetChanged();
    }
}
