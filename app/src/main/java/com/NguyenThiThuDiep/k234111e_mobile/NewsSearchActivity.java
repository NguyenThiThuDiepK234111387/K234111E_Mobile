package com.NguyenThiThuDiep.k234111e_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

/**
 * NewsSearchActivity
 * Layout: activity_news_search.xml
 *
 * Mục 4 – Voice Recognition:
 *   Nhấn "Speak" → Android mở hộp thoại nhận giọng nói
 *   → kết quả text tự điền vào edtSearch
 *
 * Mục 5 – Google Custom Search API:
 *   Nhấn "Google It" → gọi API tìm kiếm Google
 *   → hiển thị tiêu đề + snippet từng kết quả vào txtNewsContent
 *
 * API endpoint:
 *   https://www.googleapis.com/customsearch/v1
 *     ?key=<API_KEY>&cx=<SEARCH_ENGINE_ID>&q=<query>
 */
public class NewsSearchActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NewsSearchActivity";

    // ── Google Custom Search credentials ─────────────────────────────────────
    private static final String API_KEY = "AIzaSyDztlZGzj3rXbQ-bquPP_kdlMl8HVa74kw";
    private static final String CX      = "21430f756020943a7";
    private static final String SEARCH_URL =
            "https://www.googleapis.com/customsearch/v1";
    // ─────────────────────────────────────────────────────────────────────────

    // ── Views ─────────────────────────────────────────────────────────────────
    EditText edtSearch;
    ImageButton btnVoice;
    ImageButton btnSearch;
    TextView txtNewsContent;

    // ── Handler để cập nhật UI từ background thread ───────────────────────────
    Handler mainHandler = new Handler(Looper.getMainLooper());

    // ── ActivityResultLauncher cho Voice Recognition ──────────────────────────
    // (thay thế onActivityResult đã deprecated)
    ActivityResultLauncher<Intent> voiceLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {

                            // Lấy danh sách các từ nhận dạng được
                            ArrayList<String> matches = result.getData()
                                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                            if (matches != null && !matches.isEmpty()) {
                                // Lấy kết quả có độ tin cậy cao nhất (index 0)
                                String spokenText = matches.get(0);
                                edtSearch.setText(spokenText);
                                // Tự động search luôn sau khi nhận giọng
                                performSearch(spokenText);
                            }
                        }
                    });

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_search);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtSearch      = findViewById(R.id.edtSearch);
        btnVoice       = findViewById(R.id.btnVoice);
        btnSearch    = findViewById(R.id.btnSearch);
        txtNewsContent = findViewById(R.id.txtNewsContent);
    }

    private void addEvents() {

        // ── Nút Speak → mở Voice Recognition ─────────────────────────────────
        btnVoice.setOnClickListener(v -> startVoiceRecognition());

        // ── Nút Google It → gọi Search API ───────────────────────────────────
        btnSearch.setOnClickListener(v -> {
            String query = edtSearch.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Nhập hoặc nói từ khóa trước!", Toast.LENGTH_SHORT).show();
                return;
            }
            performSearch(query);
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MỤC 4 – VOICE RECOGNITION
    // Dùng RecognizerIntent (built-in Android, không cần API ngoài)
    // ─────────────────────────────────────────────────────────────────────────
    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Ngôn ngữ nhận dạng – dùng tiếng Anh (đổi "en-US" → "vi-VN" nếu muốn tiếng Việt)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hãy nói từ khóa tìm kiếm...");

        try {
            voiceLauncher.launch(intent);
        } catch (Exception e) {
            // Thiết bị không hỗ trợ Speech Recognition
            Toast.makeText(this,
                    "Thiết bị không hỗ trợ nhận dạng giọng nói", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, e.toString());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MỤC 5 – GOOGLE CUSTOM SEARCH API
    // Chạy trên background thread, kết quả trả về UI qua mainHandler
    // ─────────────────────────────────────────────────────────────────────────
    private void performSearch(String query) {
        txtNewsContent.setText("🔍 Đang tìm kiếm \"" + query + "\"...");

        Thread searchThread = new Thread(() -> {
            try {
                // Bước 1: Xây dựng URL
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                String urlString = SEARCH_URL
                        + "?key=" + API_KEY
                        + "&cx="  + CX
                        + "&q="   + encodedQuery
                        + "&num=5";          // lấy 5 kết quả

                // Bước 2: Gọi HTTP GET
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    showError("Lỗi API: HTTP " + responseCode);
                    return;
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                reader.close();
                conn.disconnect();

                // Bước 3: Parse JSON
                String result = parseSearchResults(sb.toString());

                // Bước 4: Hiển thị lên UI
                mainHandler.post(() -> txtNewsContent.setText(result));

            } catch (Exception e) {
                Log.e(LOG_TAG, e.toString());
                showError("Lỗi kết nối: " + e.getMessage());
            }
        });
        searchThread.start();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Parse JSON từ Google Custom Search API
    //
    // Cấu trúc JSON:
    // {
    //   "items": [
    //     {
    //       "title":   "Tiêu đề bài báo",
    //       "link":    "https://...",
    //       "snippet": "Mô tả ngắn..."
    //     }, ...
    //   ]
    // }
    // ─────────────────────────────────────────────────────────────────────────
    private String parseSearchResults(String jsonString) {
        try {
            JSONObject root  = new JSONObject(jsonString);
            JSONArray  items = root.optJSONArray("items");

            if (items == null || items.length() == 0) {
                return "Không tìm thấy kết quả nào.";
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item    = items.getJSONObject(i);
                String     title   = item.optString("title",   "(Không có tiêu đề)");
                String     snippet = item.optString("snippet", "");
                String     link    = item.optString("link",    "");

                sb.append("📌 ").append(i + 1).append(". ").append(title).append("\n");
                sb.append(snippet).append("\n");
                sb.append("🔗 ").append(link).append("\n");
                sb.append("\n───────────────────\n\n");
            }
            return sb.toString();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Parse error: " + e.toString());
            return "Lỗi khi đọc dữ liệu: " + e.getMessage();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    private void showError(String msg) {
        mainHandler.post(() -> {
            txtNewsContent.setText("❌ " + msg);
            Toast.makeText(NewsSearchActivity.this, msg, Toast.LENGTH_LONG).show();
        });
    }
}