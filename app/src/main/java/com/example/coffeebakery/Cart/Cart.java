package com.example.coffeebakery.Cart;

public class Cart {

    String sttgiohang;
    String giohang;
    String ma;
    String ten;
    String gia;
    String hinhanh;
    String tongtien;
    String kichthuoc;
    String ghichu;
    String soluong;
    String nguoidung;

    public Cart(){

    }

    public Cart(String sttgh, String gh, String ma, String ten, String gia, String soluong, String hinhanh, String tongtien, String kichthuoc, String ghichu, String nguoidung) {
        this.sttgiohang = sttgh;
        this.giohang = gh;
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.tongtien = tongtien;
        this.kichthuoc = kichthuoc;
        this.ghichu = ghichu;
        this.nguoidung = nguoidung;

    }
    public String getSttgiohang() {
        return sttgiohang;
    }

    public void setSttgiohang(String sttgiohang) {
        this.sttgiohang = sttgiohang;
    }
    public String getGiohang() {
        return giohang;
    }

    public void setGiohang(String giohang) {
        this.giohang = giohang;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(String kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(String nguoidung) {
        this.nguoidung = nguoidung;
    }
}
