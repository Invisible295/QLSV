package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.service.UserService;

public class LogoutUseCase {

    private final UserService userService;

    public LogoutUseCase(UserService userService) {
        this.userService = userService;
    }

    public void execute() {
        userService.logout();
    }
}
