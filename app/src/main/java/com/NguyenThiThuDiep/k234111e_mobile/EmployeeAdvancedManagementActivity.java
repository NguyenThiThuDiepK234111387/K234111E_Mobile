package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.NguyenThiThuDiep.adapters.EmployeeAdapter;
import com.NguyenThiThuDiep.models.Department;
import com.NguyenThiThuDiep.models.Employee;

import java.util.ArrayList;

public class EmployeeAdvancedManagementActivity extends AppCompatActivity {
    ListView lvEmployee;
    ArrayList<Employee> listEmployee;
    EmployeeAdapter adapterEmployee;

    Spinner spDepartment;
    ArrayList<Department> listDepartment;
    ArrayAdapter<Department> adapterDepartment;

    ImageView imgAddEmployee, imgEditEmployee, imgDeleteEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_employee_advanced_management);
        addViews();
        sampleData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Department selectedDepartment = listDepartment.get(i);
                adapterEmployee.clear();
                adapterEmployee.addAll(selectedDepartment.getListOfEmployee());
                adapterEmployee.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = spDepartment.getSelectedItemPosition();
                Department selectedDept = listDepartment.get(selectedPosition);

                // Không cho thêm nếu đang chọn "ALL"
                if (selectedDept.getId().equals("-1")) {
                    Toast.makeText(
                            EmployeeAdvancedManagementActivity.this,
                            "Vui lòng chọn một phòng ban cụ thể!",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                Intent intentAdd = new Intent(
                        EmployeeAdvancedManagementActivity.this,
                        AddEmployeeActivity.class
                );
                startActivityForResult(intentAdd, 999);
            }
        });
    }

    private void sampleData() {
        Department d0 = new Department("-1", "------ALL----");
        Department d1 = new Department("d1", "Phòng hành chính");
        Department d2 = new Department("d2", "Phòng nhân sự");
        Department d3 = new Department("d3", "Phòng tài chính");
        Department d4 = new Department("d4", "Phòng kỹ thuật");
        listDepartment.add(d0);
        listDepartment.add(d1);
        listDepartment.add(d2);
        listDepartment.add(d3);
        listDepartment.add(d4);
        d1.addEmployee(new Employee("e1", "tèo", "0981234561"));

        ArrayList<Employee> list1 = new ArrayList<>();
        list1.add(new Employee("e2", "tý", "0935235212"));
        list1.add(new Employee("e3", "bin", "0942256671"));
        list1.add(new Employee("e4", "Bo", "0910909012"));
        list1.add(new Employee("e5", "Tủn", "098789234"));
        d2.addListEmployee(list1);

        d4.addEmployee(new Employee("e6", "Tủn", "098789234"));
        d4.addEmployee(new Employee("e7", "Tủn", "098789234"));
        d4.addEmployee(new Employee("e8", "Tủn", "090789234"));
        d4.addEmployee(new Employee("e9", "Tủn", "091789234"));
        d4.addEmployee(new Employee("e10", "Tủn", "090789235"));


        adapterDepartment.notifyDataSetChanged();
    }

    private void addViews() {
        lvEmployee = findViewById(R.id.lvEmployee);
        listEmployee = new ArrayList<>();
        listEmployee.add(new Employee("e1", "tèo", "0981234561"));
        listEmployee.add(new Employee("e2", "tý", "0935235212"));
        listEmployee.add(new Employee("e3", "bin", "0942256671"));
        listEmployee.add(new Employee("e4", "Bo", "0910909012"));
        listEmployee.add(new Employee("e5", "Tủn", "098789234"));

        adapterEmployee = new EmployeeAdapter(this, R.layout.employee_custom_item);
        lvEmployee.setAdapter(adapterEmployee);

        adapterEmployee.addAll(listEmployee);
        adapterEmployee.notifyDataSetChanged();

        spDepartment = findViewById(R.id.spDepartment);
        listDepartment = new ArrayList<>();
        adapterDepartment = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);

        imgAddEmployee = findViewById(R.id.imgAddEmployee);
        imgEditEmployee = findViewById(R.id.imgEditEmployee);
        imgDeleteEmployee = findViewById(R.id.imgDeleteEmployee);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 888 && data != null) {
            Employee emp = (Employee) data.getSerializableExtra("EMPLOYEE"); // sửa key đúng

            // Lấy phòng ban đang chọn trên Spinner thay vì hardcode
            int selectedPosition = spDepartment.getSelectedItemPosition();
            Department selectedDept = listDepartment.get(selectedPosition);

            selectedDept.addEmployee(emp);

            // Cập nhật lại ListView
            adapterEmployee.clear();
            adapterEmployee.addAll(selectedDept.getListOfEmployee());
            adapterEmployee.notifyDataSetChanged();
        }
    }
}