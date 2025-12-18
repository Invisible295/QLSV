package com.teamforone.quanlysinhvien.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamforone.quanlysinhvien.R;
import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.domain.usecase.RegisterUseCase;
import com.teamforone.quanlysinhvien.service.UserService;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private RadioGroup rgRole;
    private RadioButton rbStudent, rbTeacher;
    private Button btnRegister;

    private RadioButton rbAdmin;


    private RegisterUseCase registerUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        rgRole = findViewById(R.id.rgRole);
        rbStudent = findViewById(R.id.rbStudent);
        rbTeacher = findViewById(R.id.rbTeacher);
        btnRegister = findViewById(R.id.btnRegister);
        rbAdmin = findViewById(R.id.rbAdmin);


        registerUseCase = new RegisterUseCase(new UserService(this));

        btnRegister.setOnClickListener(v -> doRegister());
    }

    private void doRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Lấy role từ RadioGroup
        User.Role role;
        int selectedId = rgRole.getCheckedRadioButtonId();
        if (selectedId == rbStudent.getId()) {
            role = User.Role.STUDENT;
        } else if (selectedId == rbTeacher.getId()) {
            role = User.Role.TEACHER;
        } else if (selectedId == rbAdmin.getId()) {
            role = User.Role.ADMIN;
        } else {
            Toast.makeText(this, "Vui lòng chọn role", Toast.LENGTH_SHORT).show();
            return;
        }


        // Gọi UseCase (và service bên trong)
        String result = registerUseCase.execute(username, password, role);

        // Hiển thị kết quả
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        // Nếu đăng ký thành công, quay về LoginActivity
        if ("Đăng ký thành công!".equals(result)) {
            finish();
        }
    }
}
