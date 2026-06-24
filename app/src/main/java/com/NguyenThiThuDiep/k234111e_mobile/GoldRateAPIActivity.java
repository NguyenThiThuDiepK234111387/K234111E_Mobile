package com.NguyenThiThuDiep.k234111e_mobile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.NguyenThiThuDiep.models.GoldRateAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GoldRateAPIActivity extends AppCompatActivity {

    private static final String API_URL =
            "https://gw.vnexpress.net/cr/?name=tygia_vangv202206";

    // ── Views (khớp activity_gold_rate_apiactivity.xml) ──────────────────────
    EditText    edtNumberOfProduct;
    Button      btnViewGoldPriceRate;
    TextView    txtPercent;
    ProgressBar progressBarPercent;
    TextView    txtStatus;
    ListView    lvRates;

    // ── Data ──────────────────────────────────────────────────────────────────
    ArrayList<GoldRateAPI>    rateItems;
    ArrayAdapter<GoldRateAPI> adapterRates;

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gold_rate_apiactivity);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    private void addViews() {
        edtNumberOfProduct   = findViewById(R.id.edtNumberOfProduct);
        btnViewGoldPriceRate = findViewById(R.id.btnViewGoldPriceRate);
        txtPercent           = findViewById(R.id.txtPercent);
        progressBarPercent   = findViewById(R.id.progressBarPercent);
        txtStatus            = findViewById(R.id.txtStatus);
        lvRates              = findViewById(R.id.lvRates);

        rateItems    = new ArrayList<>();
        adapterRates = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, rateItems);
        lvRates.setAdapter(adapterRates);
    }

    private void addEvents() {
        btnViewGoldPriceRate.setOnClickListener(v -> processFetchData());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Handler – Main Thread
    // message.arg1 = % tiến độ (0–100)
    // message.obj  = RateItem vừa parse (null = chỉ update progress bar)
    // ─────────────────────────────────────────────────────────────────────────
    Handler mainThread = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int percent = message.arg1;
            txtPercent.setText(percent + "%");
            progressBarPercent.setProgress(percent);

            if (message.obj != null) {
                GoldRateAPI item = (GoldRateAPI) message.obj;
                rateItems.add(item);
                adapterRates.notifyDataSetChanged();
            }

            if (percent == 100) {
                txtStatus.setText("✔ Đã tải xong " + rateItems.size() + " mục");
                Toast.makeText(GoldRateAPIActivity.this,
                        "Tải dữ liệu giá vàng hoàn tất!", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });

    // ─────────────────────────────────────────────────────────────────────────
    // Khởi động Background Thread
    // ─────────────────────────────────────────────────────────────────────────
    private void processFetchData() {
        String input = edtNumberOfProduct.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng muốn xem", Toast.LENGTH_SHORT).show();
            return;
        }
        int n = Integer.parseInt(input);

        // Reset UI
        rateItems.clear();
        adapterRates.notifyDataSetChanged();
        txtPercent.setText("0%");
        progressBarPercent.setProgress(0);
        txtStatus.setText("Đang kết nối...");

        Thread fetchThread = new Thread(() -> {
            try {
                // Bước 1: Gọi HTTP GET
                String jsonString = fetchJson(API_URL);
                if (jsonString == null || jsonString.isEmpty()) {
                    sendError("Không thể kết nối API. Kiểm tra Internet.");
                    return;
                }

                // Bước 2: Parse JSON theo đúng cấu trúc thực tế
                ArrayList<GoldRateAPI> allItems = parseGoldRates(jsonString);
                if (allItems.isEmpty()) {
                    sendError("Không tìm thấy dữ liệu.");
                    return;
                }

                // Bước 3: Giới hạn theo số người dùng nhập (giống bài gốc)
                int total = Math.min(n, allItems.size());

                // Bước 4: Gửi từng item lên UI qua Handler
                for (int i = 0; i < total; i++) {
                    GoldRateAPI item = allItems.get(i);
                    int percent = (i + 1) * 100 / total;

                    Message msg = mainThread.obtainMessage();
                    msg.arg1 = percent;
                    msg.obj  = item;
                    mainThread.sendMessage(msg);

                    Thread.sleep(500);
                }

                // Đảm bảo 100%
                Message finalMsg = mainThread.obtainMessage();
                finalMsg.arg1 = 100;
                mainThread.sendMessage(finalMsg);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                sendError("Lỗi: " + e.getMessage());
            }
        });
        fetchThread.start();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Parse JSON thực tế của API tygia_vangv202206
    //
    // Đường dẫn đến dữ liệu:
    //   root → "data" → "data" → "chart" → <key> → [0] (ngày mới nhất)
    //
    // Mỗi item trong mảng:
    //   { "buy": 147000, "sell": 144000, "date_label": "24/06/2026", "label": "Hà Nội PNJ" }
    // ─────────────────────────────────────────────────────────────────────────
    private ArrayList<GoldRateAPI> parseGoldRates(String jsonString) throws Exception {
        ArrayList<GoldRateAPI> list = new ArrayList<>();

        JSONObject root    = new JSONObject(jsonString);
        JSONObject data1   = root.getJSONObject("data");   // "data" cấp 1
        JSONObject data2   = data1.getJSONObject("data");  // "data" cấp 2
        JSONObject chart   = data2.getJSONObject("chart"); // "chart"

        // Duyệt tất cả các key: ha_noi_pnj, hcm_sjc, ha_noi_sjc, ...
        Iterator<String> keys = chart.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONArray arr = chart.getJSONArray(key);
            if (arr.length() == 0) continue;

            // Lấy phần tử đầu tiên = ngày mới nhất
            JSONObject obj = arr.getJSONObject(0);

            String label     = obj.optString("label",      key);   // "Hà Nội PNJ"
            String dateLabel = obj.optString("date_label", "");     // "24/06/2026"
            long   buy       = obj.optLong("buy",  0);              // 147000
            long   sell      = obj.optLong("sell", 0);              // 144000

            list.add(new GoldRateAPI(label, dateLabel, buy, sell));
        }

        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HTTP GET → String JSON thô
    // ─────────────────────────────────────────────────────────────────────────
    private String fetchJson(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        conn.disconnect();
        return sb.toString();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Gửi lỗi về UI thread
    // ─────────────────────────────────────────────────────────────────────────
    private void sendError(String errorMsg) {
        runOnUiThread(() -> {
            txtStatus.setText("✖ " + errorMsg);
            txtPercent.setText("0%");
            progressBarPercent.setProgress(0);
            Toast.makeText(GoldRateAPIActivity.this,
                    errorMsg, Toast.LENGTH_LONG).show();
        });
    }
}