package com.teamforone.quanlysinhvien.service;

import android.content.Context;

import com.teamforone.quanlysinhvien.data.dao.UserDAO;
import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.util.HashPassword;

import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;
    private User currentUser; // lưu user đang login

    public UserService(Context context) {
        userDAO = new UserDAO(context);
    }

    /**
     * Đăng ký user mới
     * @param username
     * @param password
     * @param role
     * @return thông báo thành công/thất bại
     */
    public String register(String username, String password, User.Role role) {
        if(username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                role == null) {
            return "Vui lòng điền đầy đủ thông tin!";
        }

        // Kiểm tra username đã tồn tại
        if(userDAO.isUsernameExists(username)) {
            return "Username đã tồn tại!";
        }

        // Hash password
        String hashedPassword = HashPassword.hashPassword(password);

        // Tạo user mới
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, username, hashedPassword, role);

        boolean success = userDAO.insertUser(newUser);
        return success ? "Đăng ký thành công!" : "Đăng ký thất bại!";
    }

    /**
     * Đăng nhập
     * @param username
     * @param password
     * @return thông báo
     */
    public String login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if(user == null) {
            return "Username không tồn tại!";
        }

        if(!HashPassword.verifyPassword(password, user.getPassword())) {
            return "Sai mật khẩu!";
        }

        currentUser = user;
        return "Đăng nhập thành công!";
    }

    /**
     * Đăng xuất
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Lấy user hiện đang login
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Kiểm tra user hiện tại có quyền ADMIN
     */
    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == User.Role.ADMIN;
    }

    /**
     * Kiểm tra user hiện tại có quyền TEACHER
     */
    public boolean isTeacher() {
        return currentUser != null && currentUser.getRole() == User.Role.TEACHER;
    }

    /**
     * Kiểm tra user hiện tại có quyền STUDENT
     */
    public boolean isStudent() {
        return currentUser != null && currentUser.getRole() == User.Role.STUDENT;
    }
}
