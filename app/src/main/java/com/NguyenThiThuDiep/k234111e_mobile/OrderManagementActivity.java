package com.NguyenThiThuDiep.k234111e_mobile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.adapters.OrderAdapter;
import com.NguyenThiThuDiep.models.DataWareHouse;
import com.NguyenThiThuDiep.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderManagementActivity extends AppCompatActivity {

    TextView txtFromDate;
    TextView txtToDate;
    ImageView imgFromDate;
    ImageView imgToDate;
    ImageView imgFilter;
    ListView lvOrder;
    ArrayList<Order> orders;
    //ArrayAdapter<Order>orderAdapter;
    OrderAdapter orderAdapter;

    Calendar calFrom=Calendar.getInstance();
    Calendar calTo=Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

    DatePickerDialog.OnDateSetListener dateFromListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calFrom.set(Calendar.YEAR,i);
            calFrom.set(Calendar.MONTH,i1);
            calFrom.set(Calendar.DAY_OF_MONTH,i2);
            txtFromDate.setText(sdf.format(calFrom.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener dateToListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calTo.set(Calendar.YEAR,i);
            calTo.set(Calendar.MONTH,i1);
            calTo.set(Calendar.DAY_OF_MONTH,i2);
            txtToDate.setText(sdf.format(calTo.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_order_management);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFromDatePicker();
            }
        });
        imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToDatePicker();
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFilterOrders();
            }
        });
    }

    private void processFilterOrders() {
        Date fromDate=calFrom.getTime();
        Date toDate=calTo.getTime();
        orders=DataWareHouse.filterOrdersByDate(fromDate,toDate);
        orderAdapter.clear();
        orderAdapter.addAll(orders);
        orderAdapter.notifyDataSetChanged();
    }

    private void openToDatePicker() {
        DatePickerDialog picker=new DatePickerDialog(
                OrderManagementActivity.this,
                dateToListener,
                calTo.get(Calendar.YEAR),
                calTo.get(Calendar.MONTH),
                calTo.get(Calendar.DAY_OF_MONTH)
        );
        picker.show();
    }

    private void openFromDatePicker() {
        DatePickerDialog picker=new DatePickerDialog(
                OrderManagementActivity.this,
                dateFromListener,
                calFrom.get(Calendar.YEAR),
                calFrom.get(Calendar.MONTH),
                calFrom.get(Calendar.DAY_OF_MONTH)
        );
        picker.show();
    }

    private void addViews() {
        txtFromDate=findViewById(R.id.txtFromDate);
        txtToDate=findViewById(R.id.txtToDate);
        imgFromDate=findViewById(R.id.imgFromDate);
        imgToDate=findViewById(R.id.imgToDate);
        imgFilter=findViewById(R.id.imgFilter);
        lvOrder=findViewById(R.id.lvOrder);
        orders= DataWareHouse.getOrders();
        //orderAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,orders);
        orderAdapter=new OrderAdapter(this,R.layout.order_custom_item);
        orderAdapter.addAll(orders);
        lvOrder.setAdapter(orderAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.order_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        orders.clear();
        orderAdapter.clear();
        if (item.getItemId()==R.id.mnu_all_status_order)
        {
            orders=DataWareHouse.filterOrderByStatus(com.NguyenThiThuDiep.models.OrderStatus.ALL);
        }
        else if (item.getItemId()==R.id.mnu_completed_order)
        {
            orders=DataWareHouse.filterOrderByStatus(com.NguyenThiThuDiep.models.OrderStatus.COMPLETED);
        }
        else if (item.getItemId()==R.id.mnu_customer_complain)
        {
            orders=DataWareHouse.filterOrderByStatus(com.NguyenThiThuDiep.models.OrderStatus.CUSTOMER_COMPLAIN);
        }
        else if (item.getItemId()==R.id.mnu_going_logistic)
        {
            orders=DataWareHouse.filterOrderByStatus(com.NguyenThiThuDiep.models.OrderStatus.GOING_LOGISTIC);
        }
        else if (item.getItemId()==R.id.mnu_notyet_payment)
        {
            orders=DataWareHouse.filterOrderByStatus(com.NguyenThiThuDiep.models.OrderStatus.NOT_YET_PAYMENT);
        }
        orderAdapter.addAll(orders);
        return super.onOptionsItemSelected(item);
    }
}