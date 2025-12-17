package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.service.UserService;

public class RegisterUseCase {

    private final UserService userService;

    public RegisterUseCase(UserService userService) {
        this.userService = userService;
    }

    public String execute(String username, String password, User.Role role) {
        // Chỉ gọi service
        return userService.register(username, password, role);
    }
}
