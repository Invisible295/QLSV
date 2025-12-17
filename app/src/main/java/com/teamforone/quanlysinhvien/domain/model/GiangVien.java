package com.teamforone.quanlysinhvien.domain.model;

public class GiangVien {
    private String maGV;
    private String hoTen;
    private String sdt;
    private String email;

    public GiangVien() { }

    public GiangVien(String maGV, String hoTen, String sdt, String email) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
    }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return maGV + " - " + hoTen; // Hiển thị trên List
    }
}