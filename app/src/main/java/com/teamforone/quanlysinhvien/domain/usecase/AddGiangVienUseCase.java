package com.teamforone.quanlysinhvien.domain.usecase;

import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;

public class AddGiangVienUseCase {
    private final QuanLyHocTapDAO dao;

    public AddGiangVienUseCase(QuanLyHocTapDAO dao) {
        this.dao = dao;
    }

    public boolean execute(GiangVien gv) {
        // Kiểm tra dữ liệu đầu vào (Validation)
        if (gv == null || gv.getMaGV() == null || gv.getMaGV().isEmpty()) {
            return false;
        }
        // Gọi DAO để thêm vào Database
        return dao.insertGiangVien(gv);
    }
}