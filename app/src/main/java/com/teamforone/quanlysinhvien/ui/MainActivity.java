package com.teamforone.quanlysinhvien.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.teamforone.quanlysinhvien.R;
import com.teamforone.quanlysinhvien.domain.model.User;

public class MainActivity extends AppCompatActivity {

    private CardView cardStudentManagement, cardAttendance, cardClassManagement;
    private CardView cardSubjectManagement, cardTeacherManagement, cardAccountManagement;
    private CardView cardStatistics, cardLogout;
    private TextView tvWelcome;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Lấy user từ intent
        currentUser = (User) getIntent().getSerializableExtra("user");
        if (currentUser != null) {
            tvWelcome.setText("Xin chào, " + currentUser.getUsername());
            adjustUiByRole(currentUser.getRole());
        }

        setupClickListeners();

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        showExitDialog();
                    }
                });
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        cardStudentManagement = findViewById(R.id.cardStudentManagement);
        cardAttendance = findViewById(R.id.cardAttendance);
        cardClassManagement = findViewById(R.id.cardClassManagement);
        cardSubjectManagement = findViewById(R.id.cardSubjectManagement);
        cardTeacherManagement = findViewById(R.id.cardTeacherManagement);
        cardAccountManagement = findViewById(R.id.cardAccountManagement);
        cardStatistics = findViewById(R.id.cardStatistics);
        cardLogout = findViewById(R.id.cardLogout);
    }

    private void adjustUiByRole(User.Role role) {
        switch (role) {
            case ADMIN:
                // Admin thấy tất cả
                setAllCardsVisible(true);
                break;
            case TEACHER:
                cardAccountManagement.setVisibility(View.GONE); // không quản lý tài khoản
                cardAttendance.setVisibility(View.VISIBLE);
                cardClassManagement.setVisibility(View.VISIBLE);
                cardSubjectManagement.setVisibility(View.VISIBLE);
                cardTeacherManagement.setVisibility(View.GONE); // không quản lý giáo viên khác
                cardStatistics.setVisibility(View.VISIBLE);
                cardStudentManagement.setVisibility(View.VISIBLE);
                break;
            case STUDENT:
                cardAccountManagement.setVisibility(View.GONE);
                cardAttendance.setVisibility(View.VISIBLE);
                cardClassManagement.setVisibility(View.GONE);
                cardSubjectManagement.setVisibility(View.GONE);
                cardTeacherManagement.setVisibility(View.GONE);
                cardStatistics.setVisibility(View.GONE);
                cardStudentManagement.setVisibility(View.GONE);
                break;
        }
    }

    private void setAllCardsVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        cardStudentManagement.setVisibility(visibility);
        cardAttendance.setVisibility(visibility);
        cardClassManagement.setVisibility(visibility);
        cardSubjectManagement.setVisibility(visibility);
        cardTeacherManagement.setVisibility(visibility);
        cardAccountManagement.setVisibility(visibility);
        cardStatistics.setVisibility(visibility);
        cardLogout.setVisibility(visibility);
    }

    private void setupClickListeners() {
        cardStudentManagement.setOnClickListener(v -> startActivity(new Intent(this, QuanLySinhVien.class)));
        cardClassManagement.setOnClickListener(v -> startActivity(new Intent(this, QuanLyLopActivity.class)));
        cardSubjectManagement.setOnClickListener(v -> startActivity(new Intent(this, QuanLyMonHocActivity.class)));
        cardTeacherManagement.setOnClickListener(v -> startActivity(new Intent(this, QuanLyGiangVienActivity.class)));
        // TODO: Thêm các activity khác khi sẵn sàng
        cardLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton("Có", (dialog, which) -> {
                    Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thoát ứng dụng")
                .setMessage("Bạn có muốn thoát ứng dụng?")
                .setPositiveButton("Có", (dialog, which) -> finishAffinity())
                .setNegativeButton("Không", null)
                .show();
    }
}
