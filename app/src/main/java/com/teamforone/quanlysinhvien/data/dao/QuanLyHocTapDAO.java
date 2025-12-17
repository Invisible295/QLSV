package com.teamforone.quanlysinhvien.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teamforone.quanlysinhvien.data.db.DatabaseHelper;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;
import com.teamforone.quanlysinhvien.domain.model.Lop;
import com.teamforone.quanlysinhvien.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class QuanLyHocTapDAO {
    private DatabaseHelper dbHelper;

    public QuanLyHocTapDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context, Constants.DATABASE_NAME);
    }

    // ===========================
    // 1. QUẢN LÝ LỚP HỌC
    // ===========================
    public boolean insertLop(Lop lop) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_MA_LOP, lop.getMaLop());
        values.put(Constants.COL_TEN_LOP, lop.getTenLop());
        values.put(Constants.COL_KHOA, lop.getKhoa());
        long result = db.insert(Constants.TABLE_LOP, null, values);
        return result != -1;
    }

    public boolean updateLop(Lop lop) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TEN_LOP, lop.getTenLop());
        values.put(Constants.COL_KHOA, lop.getKhoa());
        int rows = db.update(Constants.TABLE_LOP, values, Constants.COL_MA_LOP + "=?", new String[]{lop.getMaLop()});
        return rows > 0;
    }

    public boolean deleteLop(String maLop) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(Constants.TABLE_LOP, Constants.COL_MA_LOP + "=?", new String[]{maLop});
        return rows > 0;
    }

    public List<Lop> getAllLop() {
        List<Lop> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_LOP, null);
            if (cursor.moveToFirst()) {
                do {
                    Lop lop = new Lop(
                            cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_MA_LOP)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_TEN_LOP)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_KHOA))
                    );
                    list.add(lop);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public List<String> getAllLopNames() { // Dùng cho Spinner
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_LOP, null);
            if (cursor.moveToFirst()) {
                do {
                    String ma = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_MA_LOP));
                    String ten = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_TEN_LOP));
                    list.add(ma + " - " + ten);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    // ===========================
    // 2. QUẢN LÝ MÔN HỌC
    // ===========================
    public boolean insertMonHoc(String maMH, String tenMH, int tinChi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_MA_MH, maMH);
        values.put(Constants.COL_TEN_MH, tenMH);
        values.put(Constants.COL_SO_TIN_CHI, tinChi);
        return db.insert(Constants.TABLE_MONHOC, null, values) != -1;
    }

    public boolean updateMonHoc(String maMH, String tenMH, int tinChi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TEN_MH, tenMH);
        values.put(Constants.COL_SO_TIN_CHI, tinChi);
        return db.update(Constants.TABLE_MONHOC, values, Constants.COL_MA_MH + "=?", new String[]{maMH}) > 0;
    }

    public boolean deleteMonHoc(String maMH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(Constants.TABLE_MONHOC, Constants.COL_MA_MH + "=?", new String[]{maMH}) > 0;
    }

    public List<String> getAllMonHocList() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_MONHOC, null);
            if (cursor.moveToFirst()) {
                do {
                    String ma = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_MA_MH));
                    String ten = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_TEN_MH));
                    int tc = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.COL_SO_TIN_CHI));
                    list.add(ma + " - " + ten + " (" + tc + " TC)");
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public List<String> getAllMonHocNames() { // Dùng cho Spinner
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_MONHOC, null);
            if (cursor.moveToFirst()) {
                do {
                    String ma = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_MA_MH));
                    String ten = cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_TEN_MH));
                    list.add(ma + " - " + ten);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    // ===========================
    // 3. QUẢN LÝ GIẢNG VIÊN
    // ===========================
    public boolean insertGiangVien(GiangVien gv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_MA_GV, gv.getMaGV());
        values.put(Constants.COL_HO_TEN_GV, gv.getHoTen());
        values.put(Constants.COL_SDT_GV, gv.getSdt());
        return db.insert(Constants.TABLE_GIANGVIEN, null, values) != -1;
    }

    public boolean updateGiangVien(GiangVien gv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_HO_TEN_GV, gv.getHoTen());
        values.put(Constants.COL_SDT_GV, gv.getSdt());
        return db.update(Constants.TABLE_GIANGVIEN, values, Constants.COL_MA_GV + "=?", new String[]{gv.getMaGV()}) > 0;
    }

    public boolean deleteGiangVien(String maGV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(Constants.TABLE_GIANGVIEN, Constants.COL_MA_GV + "=?", new String[]{maGV}) > 0;
    }

    public List<GiangVien> getAllGiangVien() {
        List<GiangVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_GIANGVIEN, null);
            if (cursor.moveToFirst()) {
                do {
                    GiangVien gv = new GiangVien();
                    gv.setMaGV(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_MA_GV)));
                    gv.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_HO_TEN_GV)));
                    gv.setSdt(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COL_SDT_GV)));
                    list.add(gv);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    // ===========================
    // 4. PHÂN CÔNG GIẢNG DẠY
    // ===========================
    public boolean insertPhanCong(String maGV, String maMH, String maLop) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_MA_GV, maGV);
        values.put(Constants.COL_MA_MH, maMH);
        values.put(Constants.COL_MA_LOP, maLop);
        return db.insert(Constants.TABLE_PHANCONG, null, values) != -1;
    }

    public List<String> getLichDayCuaGV(String maGV) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "SELECT m.tenMH, l.tenLop FROM " + Constants.TABLE_PHANCONG + " p " +
                    "JOIN " + Constants.TABLE_MONHOC + " m ON p.maMH = m.maMH " +
                    "JOIN " + Constants.TABLE_LOP + " l ON p.maLop = l.maLop " +
                    "WHERE p.maGV = ?";
            cursor = db.rawQuery(sql, new String[]{maGV});
            if (cursor.moveToFirst()) {
                do {
                    list.add("Môn: " + cursor.getString(0) + " - Lớp: " + cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }
}