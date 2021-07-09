package com.example.coffeebakery;

public class ThongBao {
    String tieude;
    String noidung;

    public ThongBao() {
    }

    public ThongBao(String tieude, String noidung) {
        this.tieude = tieude;
        this.noidung = noidung;
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
}
