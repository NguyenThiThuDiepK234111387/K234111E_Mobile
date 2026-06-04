package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
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
        addView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addView() {
        //Step1: lấy intent mà nó mở cái màn hình này
        Intent intent=getIntent();
        //Step2: get user account from intent
        UserAccount ac=(UserAccount)intent.getSerializableExtra("LOGIN_USER");
        //Step3: show user account
        TextView txtDisplay =findViewById(R.id.txtDisplayName);
        txtDisplay.setText("Hey:"+ac.getDisplayName());
    }

    public void click_me(View view){
        String welcome=getString(R.string.str_welcome);
        Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show();
    }

    public void openCalculatorApp(View view) {
        Intent intent=new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }
}