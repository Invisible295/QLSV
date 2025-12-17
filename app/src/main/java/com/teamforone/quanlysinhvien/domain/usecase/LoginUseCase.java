package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.service.UserService;

public class LoginUseCase {

    private final UserService userService;

    public LoginUseCase(UserService userService) {
        this.userService = userService;
    }

    public User execute(String username, String password) {
        String result = userService.login(username, password);
        if ("Đăng nhập thành công!".equals(result)) {
            return userService.getCurrentUser();
        }
        return null;
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
