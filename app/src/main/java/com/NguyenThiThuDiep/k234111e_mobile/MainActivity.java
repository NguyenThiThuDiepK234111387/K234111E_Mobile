package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.models.UserAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        //Step 1: lấy intent mà nó mở màn hình này:
        Intent intent=getIntent();
        //Step 2: get User account from intent
        UserAccount ac=(UserAccount) intent.getSerializableExtra("LOGIN_USER");
        //Step 3: show User account
        TextView txtDisplay=findViewById(R.id.txtDisplayName);
        txtDisplay.setText("Hey:"+ac.getDisplayName());
    }

    public void click_me(View view) {
        String welcome=getString(R.string.str_welcome);
        Toast.makeText(this,welcome,Toast.LENGTH_LONG).show();
    }

    public void openCalculatorApp(View view) {
        Intent intent=new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void openSmsSpyware(View view) {
        Intent intent=new Intent(MainActivity.this, SMSSpywareActivity.class);
        startActivity(intent);
    }

    public void openMultiThreading(View view) {
        Intent intent=new Intent(MainActivity.this, MultiThreadingActivity.class);
        startActivity(intent);
    }

    public void openMultiThreadingObject(View view) {
        Intent intent=new Intent(MainActivity.this, MultiThreadingObjectActivity.class);
        startActivity(intent);
    }

    public void openFontAndMusic(View view) {
        Intent intent=new Intent(MainActivity.this, FontAndMusicActivity.class);
        startActivity(intent);
    }

    public void openMyContact(View view) {
        Intent intent=new Intent(MainActivity.this, MyContactActivity.class);
        startActivity(intent);
    }

    public void openViewGoldPriceRate(View view) {
        Intent intent=new Intent(MainActivity.this, GoldRateAPIActivity.class);
        startActivity(intent);
    }

    public void openNewsSearch(View view) {
        Intent intent=new Intent(MainActivity.this, NewsSearchActivity.class);
        startActivity(intent);
    }

    public void openLearnFireBase(View view) {
        Intent intent=new Intent(MainActivity.this, LearnFireBaseActivity.class);
        startActivity(intent);
    }
}