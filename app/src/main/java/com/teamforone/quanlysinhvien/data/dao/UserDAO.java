package com.teamforone.quanlysinhvien.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teamforone.quanlysinhvien.data.db.DatabaseProvider;
import com.teamforone.quanlysinhvien.domain.model.User;
import com.teamforone.quanlysinhvien.util.AppLogger;
import com.teamforone.quanlysinhvien.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final DatabaseProvider provider;

    public UserDAO(Context context){
        provider = DatabaseProvider.getInstance(context);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase database = provider.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_USER_ID, user.getUserId());
            values.put(Constants.COL_USERNAME, user.getUsername());
            values.put(Constants.COL_PASSWORD, user.getPassword());
            values.put(Constants.COL_ROLE, user.getRole().name());

            long rowId = database.insert(Constants.TABLE_USER, null, values);
            if(rowId != -1){
                AppLogger.d(Constants.LOG_TAG_USER_DAO, "Inserted: " + user);
                return true;
            }
        } catch (Exception e){
            AppLogger.e(Constants.LOG_TAG_USER_DAO, "Error inserting user", e);
        }
        return false;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase database = provider.getReadableDatabase();
        Cursor cursor = database.query(
                Constants.TABLE_USER,
                null,
                Constants.COL_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase database = provider.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_USERNAME, user.getUsername());
            values.put(Constants.COL_PASSWORD, user.getPassword());
            values.put(Constants.COL_ROLE, user.getRole().name());

            int rows = database.update(
                    Constants.TABLE_USER,
                    values,
                    Constants.COL_USER_ID + "=?",
                    new String[]{user.getUserId()}
            );

            return rows > 0;
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_USER_DAO, "Error updating user", e);
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        SQLiteDatabase database = provider.getWritableDatabase();
        try {
            int rows = database.delete(
                    Constants.TABLE_USER,
                    Constants.COL_USER_ID + "=?",
                    new String[]{userId}
            );
            return rows > 0;
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_USER_DAO, "Error deleting user", e);
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase database = provider.getReadableDatabase();
        Cursor cursor = database.query(
                Constants.TABLE_USER,
                new String[]{Constants.COL_USER_ID},
                Constants.COL_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public List<User> getAllUsers() {
        SQLiteDatabase database = provider.getReadableDatabase();
        Cursor cursor = database.query(
                Constants.TABLE_USER,
                null,
                null,
                null,
                null,
                null,
                Constants.COL_USERNAME + " ASC"
        );

        List<User> users = new ArrayList<>();
        if(cursor != null) {
            while(cursor.moveToNext()) {
                users.add(cursorToUser(cursor));
            }
            cursor.close();
        }
        return users;
    }

    private User cursorToUser(Cursor cursor){
        String userId = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_USER_ID));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_USERNAME));
        String password = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_PASSWORD));
        String roleStr = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_ROLE));

        return new User(userId, username, password, User.Role.valueOf(roleStr));
    }
}
