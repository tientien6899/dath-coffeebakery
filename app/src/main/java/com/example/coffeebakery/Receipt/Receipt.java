package com.example.coffeebakery.Receipt;

public class Receipt {
    String madon;
    String ngaydat;
    String trangthai;
    String tongtien;
    String nguoidung;
    String hoten;
    String sdt;
    String sonha;
    String ship;
    String tamtinh;
    String driverid;

    public Receipt() {
    }

    public Receipt(String madon, String ngaydat, String trangthai, String tongtien, String nguoidung,
                   String hoten, String sdt, String sonha, String ship, String tamtinh, String driverid) {
        this.madon = madon;
        this.ngaydat = ngaydat;
        this.trangthai = trangthai;
        this.tongtien = tongtien;
        this.nguoidung = nguoidung;
        this.hoten = hoten;
        this.sdt = sdt;
        this.sonha = sonha;
        this.ship = ship;
        this.tamtinh = tamtinh;
        this.driverid = driverid;
    }

    public String getMadon() {
        return madon;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(String nguoidung) {
        this.nguoidung = nguoidung;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSonha() {
        return sonha;
    }

    public void setSonha(String sonha) {
        this.sonha = sonha;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getTamtinh() {
        return tamtinh;
    }

    public void setTamtinh(String tamtinh) {
        this.tamtinh = tamtinh;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

}
