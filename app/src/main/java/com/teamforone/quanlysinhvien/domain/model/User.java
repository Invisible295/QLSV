package com.teamforone.quanlysinhvien.domain.model;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String username;
    private String password;
    private Role role;

    public enum Role {
        ADMIN,
        TEACHER,
        STUDENT
    }

    public User(String userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // ⚠️ Hạn chế dùng setter password ngoài login/register
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

