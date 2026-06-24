package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FontAndMusicActivity extends AppCompatActivity {

    // ── [THÊM MỚI] Hằng số cho SharedPreferences ─────────────────────────────
    // PREF_NAME : tên của file SharedPreferences (đặt tùy ý)
    // KEY_FONT  : key để lưu/đọc tên font đã chọn
    private static final String PREF_NAME = "FontSettings";
    private static final String KEY_FONT  = "selected_font";
    // ─────────────────────────────────────────────────────────────────────────

    Button btnPlayAudio1;
    Button btnPlayAudio2;
    TextView txtTitle;
    ListView lvFont;
    ArrayList<String> fonts;
    ArrayAdapter<String> adapterFont;
    String LOG_TAG = "FontAndMusicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_font_and_music);
        addViews();
        addEvents();
        loadFonts();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ── [THÊM MỚI] onResume – khôi phục font đã lưu ─────────────────────────
    // onResume() chạy mỗi khi Activity hiển thị lại (kể cả lần đầu sau onCreate)
    @Override
    protected void onResume() {
        super.onResume();

        // Đọc tên font đã lưu (nếu chưa có thì trả về null)
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedFont = prefs.getString(KEY_FONT, null);

        if (savedFont != null) {
            // Có font đã lưu → áp dụng lại lên txtTitle
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + savedFont);
                txtTitle.setTypeface(typeface);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Không thể khôi phục font: " + e.toString());
            }
        }
    }
    // ─────────────────────────────────────────────────────────────────────────

    private void loadFonts() {
        try {
            AssetManager assetManager = getAssets();
            String[] arrFonts = assetManager.list("fonts");
            fonts.clear();
            for (String font : arrFonts) {
                fonts.add(font);
            }
            adapterFont.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    private void addEvents() {
        btnPlayAudio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("musics/audio_success.mp3");
            }
        });
        btnPlayAudio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("musics/audio_failed.mp3");
            }
        });
        lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeFont(i);
            }
        });
    }

    private void changeFont(int i) {
        String selectedFont = adapterFont.getItem(i); // vd: "Fz-Harukaze-v2.ttf"

        // Áp dụng font lên txtTitle (giữ nguyên code gốc)
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + selectedFont);
        txtTitle.setTypeface(typeface);

        // ── [THÊM MỚI] Lưu font vào SharedPreferences ────────────────────────
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_FONT, selectedFont); // lưu tên file font
        editor.apply();                           // apply() = lưu bất đồng bộ (dùng thay cho commit())
        // ─────────────────────────────────────────────────────────────────────
    }

    private void playAudio(String audioFile) {
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(audioFile);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength()
            );
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }

    private void addViews() {
        btnPlayAudio1 = findViewById(R.id.btnPlayAudio1);
        btnPlayAudio2 = findViewById(R.id.btnPlayAudio2);
        txtTitle = findViewById(R.id.txtTitle);
        lvFont = findViewById(R.id.lvFont);
        fonts = new ArrayList<>();
        adapterFont = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fonts);
        lvFont.setAdapter(adapterFont);
    }
}