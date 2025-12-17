package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;

import java.util.List;

public class GetAllGiangVienUseCase {
    private final QuanLyHocTapDAO dao;

    public GetAllGiangVienUseCase(QuanLyHocTapDAO dao) {
        this.dao = dao;
    }

    public List<GiangVien> execute() {
        return dao.getAllGiangVien();
    }
}