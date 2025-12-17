package com.teamforone.quanlysinhvien.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.teamforone.quanlysinhvien.data.db.DatabaseProvider;
import com.teamforone.quanlysinhvien.domain.model.SinhVien;
import com.teamforone.quanlysinhvien.util.AppLogger;
import com.teamforone.quanlysinhvien.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {
    private DatabaseProvider databaseProvider;
    private Context context;

    public SinhVienDAO(Context context) {
        this.context = context.getApplicationContext();
        this.databaseProvider = DatabaseProvider.getInstance(context);
    }

    // Create
    public boolean insert(SinhVien sinhVien) {
        SQLiteDatabase db = null;
        try {
            db = databaseProvider.getWritableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return false;
            }

            ContentValues values = new ContentValues();
            values.put(Constants.COL_MA_SV, sinhVien.getMaSV());
            values.put(Constants.COL_HO_TEN, sinhVien.getHoTen());
            values.put(Constants.COL_NGAY_SINH, sinhVien.getNgaySinh());
            values.put(Constants.COL_GIOI_TINH, sinhVien.getGioiTinh());
            values.put(Constants.COL_DIA_CHI, sinhVien.getDiaChi());
            values.put(Constants.COL_MA_LOP, sinhVien.getMaLop());

            long result = db.insert(Constants.TABLE_SINHVIEN, null, values);

            if (result != -1) {
                AppLogger.d(Constants.LOG_TAG_DAO, "Inserted: " + sinhVien);
                return true;
            }
        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error inserting sinh vien: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error inserting sinh vien", e);
        }
        return false;
    }

    // Update
    public boolean update(SinhVien sinhVien) {
        SQLiteDatabase db = null;
        try {
            db = databaseProvider.getWritableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return false;
            }

            ContentValues values = new ContentValues();
            values.put(Constants.COL_HO_TEN, sinhVien.getHoTen());
            values.put(Constants.COL_NGAY_SINH, sinhVien.getNgaySinh());
            values.put(Constants.COL_GIOI_TINH, sinhVien.getGioiTinh());
            values.put(Constants.COL_DIA_CHI, sinhVien.getDiaChi());
            values.put(Constants.COL_MA_LOP, sinhVien.getMaLop());

            int result = db.update(Constants.TABLE_SINHVIEN, values,
                    Constants.COL_MA_SV + " = ?",
                    new String[]{sinhVien.getMaSV()});

            if (result > 0) {
                AppLogger.d(Constants.LOG_TAG_DAO, "Updated: " + sinhVien);
                return true;
            }
        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error updating sinh vien: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error updating sinh vien", e);
        }
        return false;
    }

    // Delete
    public boolean delete(String maSV) {
        SQLiteDatabase db = null;
        try {
            db = databaseProvider.getWritableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return false;
            }

            int result = db.delete(Constants.TABLE_SINHVIEN,
                    Constants.COL_MA_SV + " = ?",
                    new String[]{maSV});

            if (result > 0) {
                AppLogger.d(Constants.LOG_TAG_DAO, "Deleted: " + maSV);
                return true;
            }
        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error deleting sinh vien: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error deleting sinh vien", e);
        }
        return false;
    }

    // Get All
    public List<SinhVien> getAll() {
        List<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return list;
            }

            String query = "SELECT s.*, l." + Constants.COL_TEN_LOP +
                    " FROM " + Constants.TABLE_SINHVIEN + " s " +
                    "LEFT JOIN " + Constants.TABLE_LOP + " l " +
                    "ON s." + Constants.COL_MA_LOP + " = l." + Constants.COL_MA_LOP +
                    " ORDER BY s." + Constants.COL_MA_SV;

            AppLogger.d(Constants.LOG_TAG_DAO, "Executing query: " + query);
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(extractSinhVienFromCursor(cursor));
                } while (cursor.moveToNext());
            }
            AppLogger.d(Constants.LOG_TAG_DAO, "Retrieved " + list.size() + " sinh vien");

        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error getting all sinh vien: " + e.getMessage(), e);
            AppLogger.e(Constants.LOG_TAG_DAO, "Check if tables exist: " + Constants.TABLE_SINHVIEN + ", " + Constants.TABLE_LOP);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error getting all sinh vien", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return list;
    }

    // Search
    public List<SinhVien> search(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return list;
            }

            String query = "SELECT s.*, l." + Constants.COL_TEN_LOP +
                    " FROM " + Constants.TABLE_SINHVIEN + " s " +
                    "LEFT JOIN " + Constants.TABLE_LOP + " l " +
                    "ON s." + Constants.COL_MA_LOP + " = l." + Constants.COL_MA_LOP +
                    " WHERE s." + Constants.COL_MA_SV + " LIKE ? " +
                    "OR s." + Constants.COL_HO_TEN + " LIKE ? " +
                    "ORDER BY s." + Constants.COL_MA_SV;

            String searchPattern = "%" + keyword + "%";
            cursor = db.rawQuery(query, new String[]{searchPattern, searchPattern});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(extractSinhVienFromCursor(cursor));
                } while (cursor.moveToNext());
            }
            AppLogger.d(Constants.LOG_TAG_DAO, "Search '" + keyword + "' found " + list.size() + " results");

        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error searching sinh vien: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error searching sinh vien", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return list;
    }

    // Filter by Lop
    public List<SinhVien> filterByLop(String maLop) {
        List<SinhVien> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return list;
            }

            String query = "SELECT s.*, l." + Constants.COL_TEN_LOP +
                    " FROM " + Constants.TABLE_SINHVIEN + " s " +
                    "LEFT JOIN " + Constants.TABLE_LOP + " l " +
                    "ON s." + Constants.COL_MA_LOP + " = l." + Constants.COL_MA_LOP +
                    " WHERE s." + Constants.COL_MA_LOP + " = ? " +
                    "ORDER BY s." + Constants.COL_MA_SV;

            cursor = db.rawQuery(query, new String[]{maLop});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(extractSinhVienFromCursor(cursor));
                } while (cursor.moveToNext());
            }
            AppLogger.d(Constants.LOG_TAG_DAO, "Filter by " + maLop + " found " + list.size() + " results");

        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error filtering sinh vien: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error filtering sinh vien", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return list;
    }

    // Check if exists
    public boolean exists(String maSV) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return false;
            }

            cursor = db.rawQuery("SELECT 1 FROM " + Constants.TABLE_SINHVIEN +
                            " WHERE " + Constants.COL_MA_SV + " = ?",
                    new String[]{maSV});
            return cursor != null && cursor.getCount() > 0;

        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error checking existence: " + e.getMessage(), e);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error checking existence", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        return false;
    }

    // Get all Lop names
    public List<String> getAllLopNames() {
        List<String> list = new ArrayList<>();
        list.add(Constants.FILTER_ALL);

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return list;
            }

            String query = "SELECT " + Constants.COL_MA_LOP + ", " +
                    Constants.COL_TEN_LOP + " FROM " + Constants.TABLE_LOP +
                    " ORDER BY " + Constants.COL_MA_LOP;

            AppLogger.d(Constants.LOG_TAG_DAO, "Executing query: " + query);
            cursor = db.rawQuery(query, null);

            AppLogger.d(Constants.LOG_TAG_DAO, "Cursor count: " + (cursor != null ? cursor.getCount() : 0));

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String maLop = cursor.getString(0);
                    String tenLop = cursor.getString(1);
                    String item = maLop + " - " + tenLop;
                    list.add(item);
                    AppLogger.d(Constants.LOG_TAG_DAO, "Added lop: " + item);
                } while (cursor.moveToNext());
            } else {
                AppLogger.w(Constants.LOG_TAG_DAO, "No lop data found in database!");
            }

        } catch (SQLiteException e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "SQLite error getting lop names: " + e.getMessage(), e);
            AppLogger.e(Constants.LOG_TAG_DAO, "Check if table exists: " + Constants.TABLE_LOP);
        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error getting lop names", e);
        } finally {
            if (cursor != null) cursor.close();
        }

        AppLogger.d(Constants.LOG_TAG_DAO, "Total lop items: " + list.size());
        return list;
    }

    // Helper method to extract SinhVien from Cursor
    private SinhVien extractSinhVienFromCursor(Cursor cursor) {
        SinhVien sv = new SinhVien();
        sv.setMaSV(cursor.getString(0));
        sv.setHoTen(cursor.getString(1));
        sv.setNgaySinh(cursor.getString(2));
        sv.setGioiTinh(cursor.getString(3));
        sv.setDiaChi(cursor.getString(4));
        sv.setMaLop(cursor.getString(5));
        // Check if tenLop column exists (from JOIN)
        if (cursor.getColumnCount() > 6) {
            sv.setTenLop(cursor.getString(6));
        }
        return sv;
    }

    /**
     * Debug method - Kiểm tra các bảng trong database
     */
    public void debugDatabaseTables() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseProvider.getReadableDatabase();

            if (db == null || !db.isOpen()) {
                AppLogger.e(Constants.LOG_TAG_DAO, "Database is null or not open");
                return;
            }

            cursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name",
                    null
            );

            AppLogger.d(Constants.LOG_TAG_DAO, "=== DATABASE TABLES ===");
            while (cursor.moveToNext()) {
                String tableName = cursor.getString(0);
                AppLogger.d(Constants.LOG_TAG_DAO, "Table: " + tableName);
            }
            AppLogger.d(Constants.LOG_TAG_DAO, "======================");

        } catch (Exception e) {
            AppLogger.e(Constants.LOG_TAG_DAO, "Error debugging database tables", e);
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}