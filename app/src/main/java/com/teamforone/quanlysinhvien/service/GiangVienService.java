package com.teamforone.quanlysinhvien.service;

import android.content.Context;

import com.teamforone.quanlysinhvien.data.dao.QuanLyHocTapDAO;
import com.teamforone.quanlysinhvien.domain.model.GiangVien;
import com.teamforone.quanlysinhvien.domain.usecase.AddGiangVienUseCase;
import com.teamforone.quanlysinhvien.domain.usecase.DeleteGiangVienUseCase;
import com.teamforone.quanlysinhvien.domain.usecase.GetAllGiangVienUseCase;
import com.teamforone.quanlysinhvien.domain.usecase.UpdateGiangVienUseCase;

import java.util.List;

public class GiangVienService {

    private final AddGiangVienUseCase addUseCase;
    private final GetAllGiangVienUseCase getAllUseCase;
    private final UpdateGiangVienUseCase updateUseCase;
    private final DeleteGiangVienUseCase deleteUseCase;

    public GiangVienService(Context context) {
        // Khởi tạo DAO
        QuanLyHocTapDAO dao = new QuanLyHocTapDAO(context);

        // Inject DAO vào các UseCase
        this.addUseCase = new AddGiangVienUseCase(dao);
        this.getAllUseCase = new GetAllGiangVienUseCase(dao);
        this.updateUseCase = new UpdateGiangVienUseCase(dao);
        this.deleteUseCase = new DeleteGiangVienUseCase(dao);
    }

    // --- Các hàm Activity sẽ gọi ---

    public boolean addGiangVien(String ma, String ten, String sdt) {
        // Tạo đối tượng model từ dữ liệu thô
        GiangVien gv = new GiangVien(ma, ten, sdt, "");
        return addUseCase.execute(gv);
    }

    public List<GiangVien> getAllGiangVien() {
        return getAllUseCase.execute();
    }

    public boolean updateGiangVien(String ma, String ten, String sdt) {
        GiangVien gv = new GiangVien(ma, ten, sdt, "");
        return updateUseCase.execute(gv);
    }

    public boolean deleteGiangVien(String maGV) {
        return deleteUseCase.execute(maGV);
    }
}