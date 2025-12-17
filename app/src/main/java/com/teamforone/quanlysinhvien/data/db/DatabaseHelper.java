package com.teamforone.quanlysinhvien.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private final Context context;
    private static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 1; // Phiên bản DB (cập nhật nếu thay đổi)
    private static final String DATABASE_NAME = "QLSV.db"; // Tên file DB

    private static final String ASSET_DB_PATH = "databases/" + DATABASE_NAME;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context.getApplicationContext();
    }

    /**
     * Singleton để dùng chung instance
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    /**
     * Kiểm tra và copy database từ assets vào bộ nhớ trong nếu chưa có
     */
    public void prepareDatabase() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (dbFile.exists()) {
            Log.d(TAG, "Database file already exists: " + dbFile.getPath());
            return; // DB đã có, không copy lại
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            File parentFile = dbFile.getParentFile();
            if (!parentFile.exists()) {
                boolean mkdirs = parentFile.mkdirs();
                Log.d(TAG, "Database directory created: " + mkdirs);
            }

            // Copy file từ assets/databases/QLSV.db
            try (InputStream is = context.getAssets().open(ASSET_DB_PATH);
                 OutputStream os = new FileOutputStream(dbFile)) {

                byte[] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                Log.d(TAG, "Database copied successfully from assets.");
            }

        } catch (Exception e) {
            Log.e(TAG, "Failed to copy database", e);
            throw new RuntimeException("Error copying database from assets", e);
        }
    }

    /**
     * Mở database sau khi đã chuẩn bị (copy)
     */
    public SQLiteDatabase openDatabase() {
        prepareDatabase();
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return SQLiteDatabase.openDatabase(
                dbFile.getPath(),
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không dùng khi copy DB từ assets, hoặc có thể thêm nếu muốn tạo bảng mới khi DB chưa tồn tại.
        Log.d(TAG, "onCreate called - database will be created if not exist.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi nâng cấp DB
        Log.d(TAG, "onUpgrade called: " + oldVersion + " -> " + newVersion);
        // Ví dụ drop bảng và tạo lại (cẩn thận với dữ liệu)
        // db.execSQL("DROP TABLE IF EXISTS USERS");
        // onCreate(db);
    }
}
