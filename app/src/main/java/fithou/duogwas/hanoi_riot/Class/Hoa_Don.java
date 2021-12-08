package fithou.duogwas.hanoi_riot.Class;//
// Created by duogwas on 27/11/2021.
//


public class Hoa_Don {
    public String idhd;
    public String ten;
    public byte[] anh;
    public Integer gia;
    public Integer soluong;
    public String ngay;
    public Integer thanhtien;

    public Hoa_Don(String idhd, String ngay) {
        this.idhd = idhd;
        this.ngay = ngay;
    }

    public Hoa_Don(String ten, byte[] anh, Integer gia, Integer soluong) {
        this.ten = ten;
        this.anh = anh;
        this.gia = gia;
        this.soluong = soluong;
    }

    public Hoa_Don(String idhd, Integer gia, Integer soluong, Integer thanhtien, String ngay) {
        this.idhd = idhd;
        this.gia = gia;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
        this.ngay = ngay;
    }

    public Integer getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Integer thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getIdhd() {
        return idhd;
    }

    public void setIdhd(String idhd) {
        this.idhd = idhd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
