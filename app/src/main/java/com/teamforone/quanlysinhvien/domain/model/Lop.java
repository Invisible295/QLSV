package com.teamforone.quanlysinhvien.domain.model;

public class Lop {
    private String maLop;
    private String tenLop;
    private String khoa;

    public Lop() { }

    public Lop(String maLop, String tenLop, String khoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoa = khoa;
    }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }
    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }
    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }

    @Override
    public String toString() {
        return maLop + " - " + tenLop; // Hiển thị trên Spinner/List
    }
}