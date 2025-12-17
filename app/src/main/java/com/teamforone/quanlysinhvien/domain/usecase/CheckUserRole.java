package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.service.UserService;

public class CheckUserRole {

    private final UserService userService;

    public CheckUserRole(UserService userService) {
        this.userService = userService;
    }

    public boolean isAdmin() {
        User user = userService.getCurrentUser();
        return user != null && user.getRole() == User.Role.ADMIN;
    }

    public boolean isTeacher() {
        User user = userService.getCurrentUser();
        return user != null && user.getRole() == User.Role.TEACHER;
    }

    public boolean isStudent() {
        User user = userService.getCurrentUser();
        return user != null && user.getRole() == User.Role.STUDENT;
    }
}
