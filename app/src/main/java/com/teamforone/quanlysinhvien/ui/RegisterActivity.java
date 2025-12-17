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

    private EditText etUsername, etPassword, etConfirmPassword;
    private RadioGroup rgRole;
    private RadioButton rbStudent, rbTeacher;
    private Button btnRegister, btnBackToLogin;

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
        btnBackToLogin = findViewById(R.id.btnBackLogin);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        registerUseCase = new RegisterUseCase(new UserService(this));

        btnRegister.setOnClickListener(v -> doRegister());
        btnBackToLogin.setOnClickListener(v -> {
            finish(); // hoặc Intent chuyển về LoginActivity nếu cần
        });
    }

    private void doRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy role từ RadioGroup
        User.Role role;
        int selectedId = rgRole.getCheckedRadioButtonId();
        if (selectedId == rbStudent.getId()) {
            role = User.Role.STUDENT;
        } else if (selectedId == rbTeacher.getId()) {
            role = User.Role.TEACHER;
        } else {
            Toast.makeText(this, "Vui lòng chọn role", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (!password.matches(passwordPattern)) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự, 1 chữ hoa và 1 số", Toast.LENGTH_LONG).show();
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
