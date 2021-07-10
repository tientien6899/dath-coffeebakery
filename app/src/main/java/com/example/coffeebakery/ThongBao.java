package com.example.coffeebakery;

public class ThongBao {
    String tieude;
    String noidung;
    String userid;

    public ThongBao() {
    }

    public ThongBao(String tieude, String noidung, String id) {
        this.tieude = tieude;
        this.noidung = noidung;
        this.userid = id;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
