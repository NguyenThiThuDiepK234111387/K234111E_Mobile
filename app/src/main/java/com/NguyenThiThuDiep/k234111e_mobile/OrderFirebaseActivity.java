package com.NguyenThiThuDiep.k234111e_mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.NguyenThiThuDiep.adapters.OrderFirebaseAdapter;
import com.NguyenThiThuDiep.models.OrderFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderFirebaseActivity extends AppCompatActivity {

    ListView lvOrder;
    OrderFirebaseAdapter orderAdapter;
    DatabaseReference myRef;
    TextView txtFromDate, txtToDate;
    ImageView imgFromDate, imgToDate, imgFilter;
    Calendar calendarFrom, calendarTo;
    SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    SimpleDateFormat sdfIso = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    ArrayList<OrderFirebase> allOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        addViews();
        addEvents();
        loadOrdersFromFirebase();
    }

    private void addViews() {
        lvOrder = findViewById(R.id.lvOrder);
        orderAdapter = new OrderFirebaseAdapter(this, R.layout.order_custom_item);
        lvOrder.setAdapter(orderAdapter);

        txtFromDate = findViewById(R.id.txtFromDate);
        txtToDate = findViewById(R.id.txtToDate);
        imgFromDate = findViewById(R.id.imgFromDate);
        imgToDate = findViewById(R.id.imgToDate);
        imgFilter = findViewById(R.id.imgFilter);

        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        // Set default dates if needed or leave as in XML
        // txtFromDate.setText(sdfDisplay.format(calendarFrom.getTime()));
        // txtToDate.setText(sdfDisplay.format(calendarTo.getTime()));
    }

    private void addEvents() {
        imgFromDate.setOnClickListener(view -> showDatePicker(true));
        imgToDate.setOnClickListener(view -> showDatePicker(false));
        imgFilter.setOnClickListener(view -> filterOrdersByDate());

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrderFirebase selectedOrder = orderAdapter.getItem(i);
                if (selectedOrder != null) {
                    Intent intent = new Intent(OrderFirebaseActivity.this, OrderDetailFirebaseActivity.class);
                    intent.putExtra("ORDER_ID", selectedOrder.getOrderId());
                    intent.putExtra("ORDER_STATUS", selectedOrder.getStatus());
                    startActivity(intent);
                }
            }
        });
    }

    private void showDatePicker(boolean isFromDate) {
        Calendar calendar = isFromDate ? calendarFrom : calendarTo;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (isFromDate) {
                txtFromDate.setText(sdfDisplay.format(calendar.getTime()));
            } else {
                txtToDate.setText(sdfDisplay.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void filterOrdersByDate() {
        try {
            Date fromDate = sdfDisplay.parse(txtFromDate.getText().toString());
            Date toDate = sdfDisplay.parse(txtToDate.getText().toString());

            if (fromDate != null && toDate != null) {
                // Adjust toDate to end of day
                Calendar cal = Calendar.getInstance();
                cal.setTime(toDate);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                toDate = cal.getTime();

                orderAdapter.clear();
                for (OrderFirebase order : allOrders) {
                    if (order.getOrderDate() != null) {
                        try {
                            // Extract date part from ISO string "2026-06-15T08:30:00Z"
                            String dateStr = order.getOrderDate().split("T")[0];
                            Date oDate = sdfIso.parse(dateStr);
                            if (oDate != null && !oDate.before(fromDate) && !oDate.after(toDate)) {
                                orderAdapter.add(order);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadOrdersFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allOrders.clear();
                orderAdapter.clear();
                if (!snapshot.exists()) {
                    Toast.makeText(OrderFirebaseActivity.this, "No orders found in Firebase", Toast.LENGTH_LONG).show();
                    return;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderFirebase order = dataSnapshot.getValue(OrderFirebase.class);
                    if (order != null) {
                        if (order.getOrderId() == null) {
                            order.setOrderId(dataSnapshot.getKey());
                        }
                        allOrders.add(order);
                        orderAdapter.add(order);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderFirebaseActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
