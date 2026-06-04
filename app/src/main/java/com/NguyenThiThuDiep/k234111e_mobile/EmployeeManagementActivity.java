package com.NguyenThiThuDiep.k234111e_mobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EmployeeManagementActivity extends AppCompatActivity {

    ListView lvEmployee;
    ArrayList<String>listEmployee;
    ArrayAdapter<String>adapterEmployee;

    EditText edtEmployeeId,edtEmployeeName,edtPhoneNumber;
    Spinner spnBirthPlace;
    ArrayAdapter<CharSequence> adapterBirthPlace;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndex = i;
                displayEmployeeInfo(i);
            }
        });
    }
    private void displayEmployeeInfo(int position) {
        String text=listEmployee.get(position);
        //split to get the Id, name and phone:
        String []arr=text.split("-");
        String id=arr[0];
        String name=arr[1];
        String phone=arr[2];
        edtEmployeeId.setText(id);
        edtEmployeeName.setText(name);
        edtPhoneNumber.setText(phone);
        if (arr.length > 3) {
            String birthPlace = arr[3];
            int spinnerPosition = adapterBirthPlace.getPosition(birthPlace);
            spnBirthPlace.setSelection(spinnerPosition);
        }
    }

    private void addViews() {
        lvEmployee=findViewById(R.id.lvEmployee);

        listEmployee=new ArrayList<>();
        listEmployee.add("e1-tèo-0981234561-TP. Hồ Chí Minh");
        listEmployee.add("e2-tý-0935235212-An Giang");
        listEmployee.add("e3-bin-0942256671-TP. Hà Nội");
        listEmployee.add("e4-Bo-0910909012-TP. Cần Thơ");
        listEmployee.add("e5-Tủn-098789234-Đồng Nai");

        adapterEmployee=new ArrayAdapter<>
                (this,
                        android.R.layout.simple_list_item_1,
                        listEmployee);

        lvEmployee.setAdapter(adapterEmployee);

        edtEmployeeId=findViewById(R.id.edtEmployeeId);
        edtEmployeeName=findViewById(R.id.edtEmployeeName);
        edtPhoneNumber=findViewById(R.id.edtPhoneNumber);
        spnBirthPlace = findViewById(R.id.spnBirthPlace);
        adapterBirthPlace = ArrayAdapter.createFromResource(this,
                R.array.list_birthplace, android.R.layout.simple_spinner_item);
        adapterBirthPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBirthPlace.setAdapter(adapterBirthPlace);
    }

    public void closeEmployeeActivity(View view) {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        //thử lấy images ra:
        ImageView imgSave=dialog.findViewById(R.id.imgYes);
        ImageView imgCancel=dialog.findViewById(R.id.imgCancel);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void saveEmployee(View view) {
        String id = edtEmployeeId.getText().toString();
        String name = edtEmployeeName.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String birthPlace = spnBirthPlace.getSelectedItem().toString();
        String text = id + "-" + name + "-" + phone + "-" + birthPlace;

        int foundIndex = -1;
        for (int i = 0; i < listEmployee.size(); i++) {
            String existingItem = listEmployee.get(i);
            String[] arr = existingItem.split("-");
            if (arr[0].equalsIgnoreCase(id)) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex != -1) {
            listEmployee.set(foundIndex, text);
        } else {
            listEmployee.add(text);
        }
        adapterEmployee.notifyDataSetChanged();
    }

    public void deleteEmployee(View view) {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Vui lòng chọn nhân viên để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có thực sự muốn xóa [" + listEmployee.get(selectedIndex) + "] không?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listEmployee.remove(selectedIndex);
                adapterEmployee.notifyDataSetChanged();
                selectedIndex = -1;
                edtEmployeeId.setText("");
                edtEmployeeName.setText("");
                edtPhoneNumber.setText("");
                Toast.makeText(EmployeeManagementActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}