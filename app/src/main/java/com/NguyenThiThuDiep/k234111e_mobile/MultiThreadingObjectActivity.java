package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.dals.ProductDAO;
import com.NguyenThiThuDiep.models.DataWareHouse;
import com.NguyenThiThuDiep.models.Product;

import java.util.ArrayList;

public class MultiThreadingObjectActivity extends AppCompatActivity {

    EditText edtNumberOfProduct;
    TextView txtPercent;
    ProgressBar progressBarPercent;
    ListView lvProduct;
    ArrayList<Product> products;
    ArrayAdapter<Product> adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_threading_object);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                products.remove(i);
                adapterProduct.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void addViews() {
        edtNumberOfProduct = findViewById(R.id.edtNumberOfProduct);
        txtPercent = findViewById(R.id.txtPercent);
        progressBarPercent = findViewById(R.id.progressBarPercent);
        lvProduct = findViewById(R.id.lvProduct);
        products = new ArrayList<>();
        adapterProduct = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        lvProduct.setAdapter(adapterProduct);
    }

    Handler mainThread=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int percent=message.arg1;
            txtPercent.setText(percent+"%");
            progressBarPercent.setProgress(percent);
            if(message.obj!=null)
            {
                Product p = (Product) message.obj;
                products.add(p);
                adapterProduct.notifyDataSetChanged();
            }
            if(percent==100)
            {
                Toast.makeText(
                        MultiThreadingObjectActivity.this,
                        "Download Product finished",Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });
    public void processDownloadProduct(View view) {
        int n = Integer.parseInt(edtNumberOfProduct.getText().toString());
        Thread downloadProductThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Product> allProducts = ProductDAO.getProducts(MultiThreadingObjectActivity.this);
                int total = Math.min(n, allProducts.size()); // tránh vượt quá số SP có thật trong DB

                for (int i = 0; i < total; i++) {
                    Product p = allProducts.get(i);
                    int percent = (i + 1) * 100 / total;

                    Message message = mainThread.obtainMessage();
                    message.arg1 = percent;
                    message.obj = p;
                    mainThread.sendMessage(message);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message finalMessage = mainThread.obtainMessage();
                finalMessage.arg1 = 100;
                mainThread.sendMessage(finalMessage);
            }
        });
        downloadProductThread.start();
    }
}