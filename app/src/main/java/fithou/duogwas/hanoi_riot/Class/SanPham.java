package fithou.duogwas.hanoi_riot.Class;//
// Created by duogwas on 08/12/2021.
//


public class SanPham {
    public int id;
    public String TenSp;
    public Integer SoLuong;
    public byte[] HinhSp;


    public SanPham(int id, String tenSp, Integer soLuong, byte[] hinhSp) {
        this.id = id;
        TenSp = tenSp;
        SoLuong = soLuong;
        HinhSp = hinhSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getHinhSp() {
        return HinhSp;
    }

    public void setHinhSp(byte[] hinhSp) {
        HinhSp = hinhSp;
    }

    public String getTenSp() {
        return TenSp;
    }

    public void setTenSp(String tenSp) {
        TenSp = tenSp;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }
}
