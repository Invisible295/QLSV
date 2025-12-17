package com.teamforone.quanlysinhvien.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.teamforone.quanlysinhvien.R;
import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import java.util.List;

public class QuanLyMonHocActivity extends AppCompatActivity {
    private EditText edtMa, edtTen, edtTinChi;
    private Button btnAdd, btnEdit, btnDel;
    private ImageButton btnBack;
    private ListView lvMonHoc;
    private QuanLyHocTapDAO dao;
    private ArrayAdapter<String> adapter;
    private List<String> listMH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_mon_hoc);

        edtMa = findViewById(R.id.edtMaMH);
        edtTen = findViewById(R.id.edtTenMH);
        edtTinChi = findViewById(R.id.edtTinChi);
        btnAdd = findViewById(R.id.btnAddMH);
        btnEdit = findViewById(R.id.btnEditMH);
        btnDel = findViewById(R.id.btnDelMH);
        btnBack = findViewById(R.id.btnBackMH);
        lvMonHoc = findViewById(R.id.lvMonHoc);

        dao = new QuanLyHocTapDAO(this);
        loadData();

        btnBack.setOnClickListener(v -> finish());

        lvMonHoc.setOnItemClickListener((parent, view, position, id) -> {
            String item = listMH.get(position);
            String[] parts = item.split(" - ");
            if (parts.length > 0) {
                edtMa.setText(parts[0]);
                edtMa.setEnabled(false);
            }
        });

        btnAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText()) || TextUtils.isEmpty(edtTen.getText())) return;
            int tc = edtTinChi.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtTinChi.getText().toString());
            if (dao.insertMonHoc(edtMa.getText().toString(), edtTen.getText().toString(), tc)) {
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                loadData(); clearInput();
            } else Toast.makeText(this, "Lỗi! Mã trùng.", Toast.LENGTH_SHORT).show();
        });

        btnEdit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText())) return;
            int tc = edtTinChi.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtTinChi.getText().toString());
            if (dao.updateMonHoc(edtMa.getText().toString(), edtTen.getText().toString(), tc)) {
                Toast.makeText(this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                loadData(); clearInput();
            }
        });

        btnDel.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText())) return;
            if (dao.deleteMonHoc(edtMa.getText().toString())) {
                Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                loadData(); clearInput();
            }
        });
    }

    private void loadData() {
        listMH = dao.getAllMonHocList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMH);
        lvMonHoc.setAdapter(adapter);
    }

    private void clearInput() {
        edtMa.setText(""); edtTen.setText(""); edtTinChi.setText("");
        edtMa.setEnabled(true);
    }
}