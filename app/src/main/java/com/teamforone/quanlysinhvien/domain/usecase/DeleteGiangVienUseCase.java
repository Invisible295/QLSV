package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;

public class DeleteGiangVienUseCase {
    private final QuanLyHocTapDAO dao;

    public DeleteGiangVienUseCase(QuanLyHocTapDAO dao) {
        this.dao = dao;
    }

    public boolean execute(String maGV) {
        return dao.deleteGiangVien(maGV);
    }
}