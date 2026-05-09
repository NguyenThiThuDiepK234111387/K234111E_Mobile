package com.NguyenThiThuDiep.k234111e_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    Button btnCalculate, btnC, btnCE, btnPercent, btn1_x, btnx_2, btn_xsqrt, btnChangeSign;
    TextView txtMC, txtMR, txtMPlus, txtMMinus, txtMS, txtM;
    View.OnClickListener click_m_listener;

    private double memoryValue = 0;

    private static final String PREF_NAME = "calculator_prefs";
    private static final String KEY_FORMULAR = "saved_formular";
    private static final String KEY_MEMORY = "saved_memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedFormular = prefs.getString(KEY_FORMULAR, "");
        memoryValue = Double.longBitsToDouble(prefs.getLong(KEY_MEMORY, Double.doubleToLongBits(0)));
        EditText edtFormular = findViewById(R.id.edtFormular);
        if (edtFormular != null) {
            edtFormular.setText(savedFormular);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText edtFormular = findViewById(R.id.edtFormular);
        String currentFormular = (edtFormular != null) ? edtFormular.getText().toString() : "";
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_FORMULAR, currentFormular)
                .putLong(KEY_MEMORY, Double.doubleToLongBits(memoryValue))
                .apply();
    }

    private void addEvents() {
        btnCalculate.setOnClickListener(view -> processFormular());

        btnC.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular != null) edtFormular.setText("");
        });

        btnCE.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular != null) edtFormular.setText("");
        });

        btnPercent.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString();
            if (text.isEmpty()) return;
            try {
                String lastNum = getLastNumber(text);
                if (lastNum.isEmpty()) return;
                double value = Double.parseDouble(lastNum);
                String newText = replaceLastNumber(text, formatNumber(value / 100.0));
                edtFormular.setText(newText);
            } catch (NumberFormatException e) {
                edtFormular.setText("E");
            }
        });

        btn1_x.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString().trim();
            if (text.isEmpty()) return;
            try {
                double value = Double.parseDouble(text);
                if (value == 0) { edtFormular.setText("E"); return; }
                edtFormular.setText(formatNumber(1.0 / value));
            } catch (NumberFormatException e) {
                edtFormular.setText("E");
            }
        });

        btnx_2.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString().trim();
            if (text.isEmpty()) return;
            try {
                double value = Double.parseDouble(text);
                edtFormular.setText(formatNumber(value * value));
            } catch (NumberFormatException e) {
                edtFormular.setText("E");
            }
        });

        btn_xsqrt.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString().trim();
            if (text.isEmpty()) return;
            try {
                double value = Double.parseDouble(text);
                if (value < 0) { edtFormular.setText("E"); return; }
                edtFormular.setText(formatNumber(Math.sqrt(value)));
            } catch (NumberFormatException e) {
                edtFormular.setText("E");
            }
        });

        btnChangeSign.setOnClickListener(view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString();
            if (text.isEmpty()) return;
            try {
                double value = Double.parseDouble(text.trim());
                edtFormular.setText(formatNumber(-value));
            } catch (NumberFormatException e) {
                String lastNum = getLastNumber(text);
                if (!lastNum.isEmpty()) {
                    try {
                        double lastVal = Double.parseDouble(lastNum);
                        edtFormular.setText(replaceLastNumber(text, formatNumber(-lastVal)));
                    } catch (NumberFormatException ex) {
                        edtFormular.setText("E");
                    }
                }
            }
        });

        click_m_listener = view -> {
            EditText edtFormular = findViewById(R.id.edtFormular);
            if (edtFormular == null) return;
            String text = edtFormular.getText().toString().trim();

            if (view.getId() == txtMC.getId()) {
                memoryValue = 0;
            } else if (view.getId() == txtMR.getId()) {
                edtFormular.setText(formatNumber(memoryValue));
            } else if (view.getId() == txtMPlus.getId()) {
                if (!text.isEmpty()) {
                    try { memoryValue += Double.parseDouble(text); } catch (NumberFormatException ignored) {}
                }
            } else if (view.getId() == txtMMinus.getId()) {
                if (!text.isEmpty()) {
                    try { memoryValue -= Double.parseDouble(text); } catch (NumberFormatException ignored) {}
                }
            } else if (view.getId() == txtMS.getId()) {
                if (!text.isEmpty()) {
                    try { memoryValue = Double.parseDouble(text); } catch (NumberFormatException ignored) {}
                }
            } else if (view.getId() == txtM.getId()) {
                edtFormular.setText(formatNumber(memoryValue));
            }
        };

        txtM.setOnClickListener(click_m_listener);
        txtMC.setOnClickListener(click_m_listener);
        txtMMinus.setOnClickListener(click_m_listener);
        txtMR.setOnClickListener(click_m_listener);
        txtMPlus.setOnClickListener(click_m_listener);
        txtMS.setOnClickListener(click_m_listener);
    }

    private void processFormular() {
        EditText edtFormular = findViewById(R.id.edtFormular);
        if (edtFormular == null) return;
        String formular = edtFormular.getText().toString().trim();
        if (formular.isEmpty()) return;
        try {
            double result = evaluate(formular);
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                edtFormular.setText("E");
            } else {
                edtFormular.setText(formatNumber(result));
            }
        } catch (Exception e) {
            edtFormular.setText("E");
        }
    }

    private double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", "");
        // Không cho biểu thức kết thúc bằng toán tử
        while (!expression.isEmpty() && isOperatorChar(expression.charAt(expression.length() - 1))) {
            expression = expression.substring(0, expression.length() - 1);
        }
        if (expression.isEmpty()) throw new RuntimeException("Empty expression");
        java.util.List<String> tokens = tokenize(expression);
        if (tokens.isEmpty()) throw new RuntimeException("No tokens");
        tokens = applyHighPriority(tokens);
        return applyLowPriority(tokens);
    }

    private boolean isOperatorChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == ':';
    }

    private java.util.List<String> tokenize(String expr) {
        java.util.List<String> tokens = new java.util.ArrayList<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == ':') {
                // Dấu '-' đứng đầu hoặc sau toán tử → là số âm
                if (c == '-' && (tokens.isEmpty() || isOperator(tokens.get(tokens.size() - 1)))) {
                    StringBuilder num = new StringBuilder("-");
                    i++;
                    while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                        num.append(expr.charAt(i));
                        i++;
                    }
                    if (num.length() == 1) throw new RuntimeException("Lone minus sign");
                    tokens.add(num.toString());
                } else {
                    tokens.add(String.valueOf(c));
                    i++;
                }
            } else if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i));
                    i++;
                }
                tokens.add(num.toString());
            } else {
                throw new RuntimeException("Unknown character: " + c);
            }
        }
        return tokens;
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals(":");
    }

    // FIX: kiểm tra i + 1 tồn tại trước khi lấy phần tử
    private java.util.List<String> applyHighPriority(java.util.List<String> tokens) {
        java.util.List<String> result = new java.util.ArrayList<>(tokens);
        int i = 1;
        while (i < result.size()) {
            String op = result.get(i);
            if (op.equals("*") || op.equals(":")) {
                if (i + 1 >= result.size()) throw new RuntimeException("Missing right operand");
                double left = Double.parseDouble(result.get(i - 1));
                double right = Double.parseDouble(result.get(i + 1));
                double val;
                if (op.equals("*")) {
                    val = left * right;
                } else {
                    if (right == 0) throw new ArithmeticException("Division by zero");
                    val = left / right;
                }
                result.set(i - 1, formatNumber(val));
                result.remove(i); // xóa operator
                result.remove(i); // xóa right operand
                // không tăng i, xét lại vị trí i-1 mới
            } else {
                i += 2;
            }
        }
        return result;
    }

    // FIX: kiểm tra i + 1 tồn tại trước khi lấy phần tử
    private double applyLowPriority(java.util.List<String> tokens) {
        if (tokens.isEmpty()) throw new RuntimeException("Empty token list");
        double result = Double.parseDouble(tokens.get(0));
        int i = 1;
        while (i < tokens.size()) {
            if (i + 1 >= tokens.size()) throw new RuntimeException("Missing right operand");
            String op = tokens.get(i);
            double right = Double.parseDouble(tokens.get(i + 1));
            if (op.equals("+")) result += right;
            else if (op.equals("-")) result -= right;
            else throw new RuntimeException("Unknown operator: " + op);
            i += 2;
        }
        return result;
    }

    private String getLastNumber(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder num = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                num.insert(0, c);
            } else if (c == '-' && i == 0) {
                // Dấu trừ đứng đầu chuỗi → thuộc số âm
                num.insert(0, c);
                break;
            } else if (c == '-' && i > 0 && isOperatorChar(text.charAt(i - 1))) {
                // Dấu trừ ngay sau toán tử → số âm
                num.insert(0, c);
                break;
            } else {
                break;
            }
        }
        return num.toString();
    }

    private String replaceLastNumber(String text, String newNum) {
        String lastNum = getLastNumber(text);
        if (lastNum.isEmpty()) return text + newNum;
        return text.substring(0, text.length() - lastNum.length()) + newNum;
    }

    // FIX: tránh overflow khi cast sang long với số rất lớn
    private String formatNumber(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return "E";
        if (value == Math.floor(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

    private void addViews() {
        btnCalculate  = findViewById(R.id.btnCalculate);
        btnC          = findViewById(R.id.btnC);
        btnCE         = findViewById(R.id.btnCE);
        btnPercent    = findViewById(R.id.btnPercent);
        btn1_x        = findViewById(R.id.btn1_x);
        btnx_2        = findViewById(R.id.btnx_2);
        btn_xsqrt     = findViewById(R.id.btn_xsqrt);
        btnChangeSign = findViewById(R.id.btnChangeSign);
        txtMC         = findViewById(R.id.txtMC);
        txtMR         = findViewById(R.id.txtMR);
        txtMPlus      = findViewById(R.id.txtMPlus);
        txtMMinus     = findViewById(R.id.txtMMinus);
        txtMS         = findViewById(R.id.txtMS);
        txtM          = findViewById(R.id.txtM);
    }

    // FIX: kiểm tra view là Button trước khi cast
    public void processChooseValue(View view) {
        if (!(view instanceof Button)) return;
        Button btn = (Button) view;
        EditText edtFormular = findViewById(R.id.edtFormular);
        if (edtFormular == null) return;

        String old_value = edtFormular.getText().toString();
        String new_value = btn.getText().toString();

        if (new_value == null || new_value.isEmpty()) return;

        // Nếu màn hình đang hiện lỗi, xóa và nhập mới
        if (old_value.equals("E")) {
            edtFormular.setText(new_value);
            return;
        }

        // Không cho phép hai toán tử liên tiếp (trừ dấu '-' đứng đầu)
        if (!old_value.isEmpty()) {
            char last = old_value.charAt(old_value.length() - 1);
            boolean lastIsOp = (last == '+' || last == '-' || last == '*' || last == ':');
            boolean newIsOp = (new_value.equals("+") || new_value.equals("-") || new_value.equals("*") || new_value.equals(":"));
            if (lastIsOp && newIsOp) {
                edtFormular.setText(old_value.substring(0, old_value.length() - 1) + new_value);
                return;
            }
        }

        // Không cho thêm dấu '.' nếu số hiện tại đã có rồi
        if (new_value.equals(".")) {
            String lastNum = getLastNumber(old_value);
            if (lastNum.contains(".")) return;
            // Nếu chưa có số trước dấu chấm, thêm '0' trước
            if (lastNum.isEmpty()) {
                edtFormular.setText(old_value + "0.");
                return;
            }
        }

        edtFormular.setText(old_value + new_value);
    }

    public void processBackspace(View view) {
        EditText edtFormular = findViewById(R.id.edtFormular);
        if (edtFormular == null) return;
        String old_value = edtFormular.getText().toString();
        if (old_value.equals("E")) {
            edtFormular.setText("");
            return;
        }
        if (old_value.length() > 1) {
            edtFormular.setText(old_value.substring(0, old_value.length() - 1));
        } else {
            edtFormular.setText("");
        }
    }
}