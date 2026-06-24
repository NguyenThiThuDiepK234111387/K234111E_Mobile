package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.dals.MyContactDAO;
import com.NguyenThiThuDiep.models.MyContact;

import java.util.ArrayList;

public class MyContactActivity extends AppCompatActivity {
    ListView lvMyContact;
    ArrayList<MyContact> contacts;
    ArrayAdapter<MyContact> adapterMyContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_contact);
        // Xin quyền gọi điện
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
        addViews();
        loadMyContacts();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvMyContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyContact contact=contacts.get(i);
                processDirectCall(contact);
            }
        });
    }

    private void processDirectCall(MyContact contact) {
        String phone=contact.getPhone();
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }

    private void loadMyContacts() {
        contacts.clear();
        contacts= MyContactDAO.getMyContacts(this);
        adapterMyContact.clear();
        adapterMyContact.addAll(contacts);
        adapterMyContact.notifyDataSetChanged();
    }

    private void addViews() {
        lvMyContact=findViewById(R.id.lvMyContact);
        contacts=new ArrayList<>();
        adapterMyContact=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                contacts);
        lvMyContact.setAdapter(adapterMyContact);
    }
}