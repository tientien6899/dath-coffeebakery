package com.example.coffeebakery.Home;

public class TapChi {
    String tieude, noidung, hinhanh, ngaydang;

    public TapChi() {
    }

    public TapChi(String tieude, String noidung, String hinhanh, String ngaydang) {
        this.tieude = tieude;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
        this.ngaydang = ngaydang;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }
}
