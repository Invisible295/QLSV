package com.teamforone.quanlysinhvien.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.teamforone.quanlysinhvien.BuildConfig;

import java.nio.charset.StandardCharsets;

public final class DatabaseProvider {

    private final DatabaseHelper databaseHelper;
    private static DatabaseProvider instance;

    // Private constructor
    private DatabaseProvider(Context context) {
        // Lấy instance DatabaseHelper (Singleton)
        databaseHelper = DatabaseHelper.getInstance(context.getApplicationContext());
        // Chuẩn bị database (copy từ assets nếu chưa có)
        databaseHelper.prepareDatabase();
    }

    /**
     * Lấy instance DatabaseProvider (Singleton)
     * @param context Context của ứng dụng
     * @return instance DatabaseProvider
     */
    public static synchronized DatabaseProvider getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseProvider(context);
        }
        return instance;
    }

    /**
     * Lấy đối tượng SQLiteDatabase để đọc
     */
    public SQLiteDatabase getReadableDatabase() {
        return databaseHelper.getReadableDatabase();
    }

    /**
     * Lấy đối tượng SQLiteDatabase để ghi
     */
    public SQLiteDatabase getWritableDatabase() {
        return databaseHelper.getWritableDatabase();
    }

    /**
     * Đóng database khi không sử dụng nữa
     */
    public void close() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    /**
     * Lấy đường dẫn file DB trong thư mục assets (ví dụ "databases/QLSV.db")
     * Giá trị này lấy từ BuildConfig (mã hóa base64) để bảo mật tên file
     */
    public static String getAssetDbPath() {
        return new String(
                Base64.decode(BuildConfig.DB_NAME_ENCODED, Base64.NO_WRAP),
                StandardCharsets.UTF_8
        );
    }

    /**
     * Lấy tên file DB (ví dụ "QLSV.db") từ đường dẫn trong assets
     */
    public static String getDbName() {
        String fullPath = getAssetDbPath();
        return fullPath.substring(fullPath.lastIndexOf('/') + 1);
    }
}
