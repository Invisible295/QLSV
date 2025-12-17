package com.teamforone.quanlysinhvien.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
public class HashPassword {
    // Hash password
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    // Kiểm tra password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
