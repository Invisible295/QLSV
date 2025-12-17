package com.teamforone.quanlysinhvien.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.teamforone.quanlysinhvien.R;
import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;
import java.util.List;

public class QuanLyGiangVienActivity extends AppCompatActivity {
    private EditText edtMa, edtTen, edtSdt;
    private Spinner spnMonHoc, spnLop;
    private Button btnAdd, btnEdit, btnDel, btnPhanCong;
    private ListView lvGiangVien;
    private ImageButton btnBack;

    private QuanLyHocTapDAO dao;
    private ArrayAdapter<GiangVien> adapterGV;
    private List<GiangVien> listGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_giang_vien);

        edtMa = findViewById(R.id.edtMaGV);
        edtTen = findViewById(R.id.edtTenGV);
        edtSdt = findViewById(R.id.edtSdtGV);
        spnMonHoc = findViewById(R.id.spnMonHoc);
        spnLop = findViewById(R.id.spnLop);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnUpdate);
        btnDel = findViewById(R.id.btnDelete);
        btnPhanCong = findViewById(R.id.btnPhanCong);
        lvGiangVien = findViewById(R.id.lvGiangVien);
        btnBack = findViewById(R.id.btnBackGV);

        dao = new QuanLyHocTapDAO(this);
        loadAllData();

        btnBack.setOnClickListener(v -> finish());

        // CLICK LIST VIEW
        lvGiangVien.setOnItemClickListener((parent, view, position, id) -> {
            GiangVien gv = listGV.get(position);
            edtMa.setText(gv.getMaGV());
            edtTen.setText(gv.getHoTen());
            edtSdt.setText(gv.getSdt());
            edtMa.setEnabled(false);

            // Hiện lịch dạy
            List<String> lichDay = dao.getLichDayCuaGV(gv.getMaGV());
            String msg = lichDay.isEmpty() ? "Chưa có lịch dạy." : "Lịch dạy:\n- " + String.join("\n- ", lichDay);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        });

        // THÊM GV
        btnAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText())) return;
            GiangVien gv = new GiangVien(edtMa.getText().toString(), edtTen.getText().toString(), edtSdt.getText().toString(), "");
            if (dao.insertGiangVien(gv)) {
                Toast.makeText(this, "Thêm GV thành công!", Toast.LENGTH_SHORT).show();
                loadAllData(); clearInput();
            } else Toast.makeText(this, "Lỗi! Mã GV đã tồn tại.", Toast.LENGTH_SHORT).show();
        });

        // SỬA GV
        btnEdit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText())) return;
            GiangVien gv = new GiangVien(edtMa.getText().toString(), edtTen.getText().toString(), edtSdt.getText().toString(), "");
            if (dao.updateGiangVien(gv)) {
                Toast.makeText(this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                loadAllData(); clearInput();
            }
        });

        // XÓA GV
        btnDel.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtMa.getText())) return;
            if (dao.deleteGiangVien(edtMa.getText().toString())) {
                Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                loadAllData(); clearInput();
            }
        });

        // PHÂN CÔNG GIẢNG DẠY
        btnPhanCong.setOnClickListener(v -> {
            String maGV = edtMa.getText().toString();
            if (TextUtils.isEmpty(maGV)) {
                Toast.makeText(this, "Chưa chọn Giảng viên!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (spnMonHoc.getSelectedItem() == null || spnLop.getSelectedItem() == null) {
                Toast.makeText(this, "Chưa có dữ liệu Môn/Lớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            String maMH = spnMonHoc.getSelectedItem().toString().split(" - ")[0];
            String maLop = spnLop.getSelectedItem().toString().split(" - ")[0];

            if (dao.insertPhanCong(maGV, maMH, maLop)) {
                new AlertDialog.Builder(this)
                        .setTitle("Thành công")
                        .setMessage("Đã phân công GV " + maGV + " dạy môn " + maMH + " cho lớp " + maLop)
                        .setPositiveButton("OK", null).show();
            } else {
                Toast.makeText(this, "Lỗi phân công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllData() {
        // Load List GV
        listGV = dao.getAllGiangVien();
        adapterGV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listGV);
        lvGiangVien.setAdapter(adapterGV);

        // Load Spinner
        List<String> listMon = dao.getAllMonHocNames();
        if (listMon.isEmpty()) listMon.add("Chưa có môn - Vui lòng tạo");
        spnMonHoc.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listMon));

        List<String> listLop = dao.getAllLopNames();
        if (listLop.isEmpty()) listLop.add("Chưa có lớp - Vui lòng tạo");
        spnLop.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listLop));
    }

    private void clearInput() {
        edtMa.setText(""); edtTen.setText(""); edtSdt.setText("");
        edtMa.setEnabled(true);
    }
}