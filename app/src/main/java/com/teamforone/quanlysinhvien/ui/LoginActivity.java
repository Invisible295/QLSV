package com.teamforone.quanlysinhvien.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamforone.quanlysinhvien.R;
import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.domain.usecase.LoginUseCase;
import com.teamforone.quanlysinhvien.service.UserService;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    private LoginUseCase loginUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        loginUseCase = new LoginUseCase(new UserService(this)); // truyền context nếu cần

        btnLogin.setOnClickListener(v -> doLogin());
        btnRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void doLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Thực hiện login qua UseCase
            User user = loginUseCase.execute(username, password);

            if (user != null) {
                // Login thành công, log thông tin user để debug
                Log.d("LoginDebug", "Login successful: " + user.getUsername() + ", Role: " + user.getRole());

                // Chuyển sang MainActivity và truyền User
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", user); // User implements Serializable
                startActivity(intent);
                finish();
            } else {
                // Login thất bại
                Log.d("LoginDebug", "Login failed for username: " + username);
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Bắt tất cả lỗi bất ngờ
            Log.e("LoginDebug", "Error during login", e);
            Toast.makeText(this, "Đã xảy ra lỗi khi đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }

}

