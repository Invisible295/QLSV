package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;

public class UpdateGiangVienUseCase {
    private final QuanLyHocTapDAO dao;

    public UpdateGiangVienUseCase(QuanLyHocTapDAO dao) {
        this.dao = dao;
    }

    public boolean execute(GiangVien gv) {
        return dao.updateGiangVien(gv);
    }
}