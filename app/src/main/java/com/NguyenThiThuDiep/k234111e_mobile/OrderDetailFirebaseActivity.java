package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.NguyenThiThuDiep.adapters.OrderDetailFirebaseAdapter;
import com.NguyenThiThuDiep.models.OrderDetailFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class OrderDetailFirebaseActivity extends AppCompatActivity {

    TextView txtDetailOrderId, txtTotalAmount;
    ListView lvOrderDetail;
    OrderDetailFirebaseAdapter adapter;
    String orderId;
    String status;
    android.widget.LinearLayout layoutOrderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_firebase);

        orderId = getIntent().getStringExtra("ORDER_ID");
        status = getIntent().getStringExtra("ORDER_STATUS");

        addViews();
        applyStatusColor();
        loadOrderDetails();
    }

    private void addViews() {
        txtDetailOrderId = findViewById(R.id.txtDetailOrderId);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        lvOrderDetail = findViewById(R.id.lvOrderDetail);
        layoutOrderDetail = findViewById(R.id.layoutOrderDetail);

        txtDetailOrderId.setText("Order ID: " + orderId);
        adapter = new OrderDetailFirebaseAdapter(this, R.layout.order_detail_item);
        lvOrderDetail.setAdapter(adapter);
    }

    private void applyStatusColor() {
        if (status == null) return;
        
        int colorResId;
        switch (status.toLowerCase()) {
            case "completed":
                colorResId = R.color.order_completed;
                break;
            case "shipping":
                colorResId = R.color.order_shipping;
                break;
            case "pending":
                colorResId = R.color.order_pending;
                break;
            case "cancelled":
                colorResId = R.color.order_cancelled;
                break;
            case "going logistic":
                colorResId = R.color.order_going_logistic;
                break;
            case "not yet payment":
                colorResId = R.color.order_not_yet_payment;
                break;
            case "customer complain":
                colorResId = R.color.order_customer_complain;
                break;
            case "processing":
                colorResId = R.color.order_processing;
                break;
            default:
                colorResId = R.color.white;
                break;
        }
        layoutOrderDetail.setBackgroundColor(getColor(colorResId));
        
        // Ensure text is black for readability
        int black = getColor(R.color.black);
        txtDetailOrderId.setTextColor(black);
        txtTotalAmount.setTextColor(black);
    }

    private void loadOrderDetails() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // According to the structure, all orderDetails are in a flat list under "orderDetails"
        // and each has an "orderId" field to filter by.
        DatabaseReference myRef = database.getReference("orderDetails");
        Query query = myRef.orderByChild("orderId").equalTo(orderId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                double total = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderDetailFirebase detail = dataSnapshot.getValue(OrderDetailFirebase.class);
                    if (detail != null) {
                        if (detail.getOrderDetailId() == null) {
                            detail.setOrderDetailId(dataSnapshot.getKey());
                        }
                        adapter.add(detail);
                        total += (detail.getUnitPrice() * detail.getQuantity());
                    }
                }
                txtTotalAmount.setText(String.format(Locale.getDefault(), "Total: %,.0f", total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderDetailFirebaseActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
