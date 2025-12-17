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
import com.teamforone.quanlysinhvien.domain.model.Lop;

import java.util.List;

public class QuanLyLopActivity extends AppCompatActivity {

    private EditText edtMa, edtTen, edtKhoa;
    private Button btnAdd, btnEdit, btnDel;
    private ImageButton btnBack;
    private ListView lvLop;

    private QuanLyHocTapDAO dao;
    private ArrayAdapter<Lop> adapter;
    private List<Lop> listLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lop); // Đảm bảo trùng tên file XML ở trên

        initViews();
        dao = new QuanLyHocTapDAO(this); // Gọi DAO tổng hợp
        loadData();
        addEvents();
    }

    private void initViews() {
        edtMa = findViewById(R.id.edtMaLop);
        edtTen = findViewById(R.id.edtTenLop);
        edtKhoa = findViewById(R.id.edtKhoa);
        btnAdd = findViewById(R.id.btnAddLop);
        btnEdit = findViewById(R.id.btnEditLop);
        btnDel = findViewById(R.id.btnDelLop);
        btnBack = findViewById(R.id.btnBack);
        lvLop = findViewById(R.id.lvLop);
    }

    private void loadData() {
        listLop = dao.getAllLop();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listLop);
        lvLop.setAdapter(adapter);
    }

    private void addEvents() {
        // Nút Back
        btnBack.setOnClickListener(v -> finish());

        // Click vào dòng trong danh sách
        lvLop.setOnItemClickListener((parent, view, position, id) -> {
            Lop selectedLop = listLop.get(position);
            edtMa.setText(selectedLop.getMaLop());
            edtTen.setText(selectedLop.getTenLop());
            edtKhoa.setText(selectedLop.getKhoa());
            edtMa.setEnabled(false); // Khóa không cho sửa Mã lớp khi đang chọn
        });

        // Nút Thêm
        btnAdd.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String khoa = edtKhoa.getText().toString().trim();

            if (TextUtils.isEmpty(ma) || TextUtils.isEmpty(ten)) {
                Toast.makeText(this, "Vui lòng nhập Mã và Tên lớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            Lop lopMoi = new Lop(ma, ten, khoa);
            if (dao.insertLop(lopMoi)) {
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                loadData();
                clearInput();
            } else {
                Toast.makeText(this, "Lỗi! Mã lớp đã tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút Sửa
        btnEdit.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String khoa = edtKhoa.getText().toString().trim();

            if (TextUtils.isEmpty(ma)) {
                Toast.makeText(this, "Chưa chọn lớp để sửa!", Toast.LENGTH_SHORT).show();
                return;
            }

            Lop lopUpdate = new Lop(ma, ten, khoa);
            if (dao.updateLop(lopUpdate)) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                loadData();
                clearInput();
            }
        });

        // Nút Xóa
        btnDel.setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            if (TextUtils.isEmpty(ma)) return;

            if (dao.deleteLop(ma)) {
                Toast.makeText(this, "Đã xóa lớp!", Toast.LENGTH_SHORT).show();
                loadData();
                clearInput();
            }
        });
    }

    private void clearInput() {
        edtMa.setText("");
        edtTen.setText("");
        edtKhoa.setText("");
        edtMa.setEnabled(true);
        edtMa.requestFocus();
    }
}