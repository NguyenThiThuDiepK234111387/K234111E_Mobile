package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.NguyenThiThuDiep.models.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LearnFireBaseActivity extends AppCompatActivity {
    ListView lvContact;
    ArrayAdapter<String> contactAdapter;
    DatabaseHelper dbHelper; // ✅ khai báo ở đây
    String TAG = "FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_fire_base);
        dbHelper = new DatabaseHelper(this); // ✅ khởi tạo ở đây
        addViews();
        addEvents();
    }

    private void addViews() {
        lvContact = findViewById(R.id.lvContact);
        contactAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvContact.setAdapter(contactAdapter);
        loadData();
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            // ONLINE: đọc Firebase rồi lưu SQLite
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("contacts");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contactAdapter.clear();
                    dbHelper.deleteAllContacts();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String key = data.getKey();
                        String value = data.getValue().toString();
                        dbHelper.insertContact(key, value); // lưu vào SQLite
                        contactAdapter.add(key + "\n" + value);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            });
        } else {
            // OFFLINE: đọc SQLite
            List<String> contacts = dbHelper.getAllContacts();
            contactAdapter.clear();
            for (String contact : contacts) {
                contactAdapter.add(contact);
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public void processInsertContact(View view) {
        Intent intent = new Intent(LearnFireBaseActivity.this, InsertContactActivity.class);
        startActivity(intent);
    }

    private void addEvents() {
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = contactAdapter.getItem(position);
                String key = data.split("\n")[0];
                Intent intent = new Intent(LearnFireBaseActivity.this, DetailContactActivity.class);
                intent.putExtra("KEY", key);
                startActivity(intent);
            }
        });
    }
}