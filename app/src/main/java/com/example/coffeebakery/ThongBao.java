package com.example.coffeebakery;

import com.google.firebase.database.DataSnapshot;

public class ThongBao {
    int mathongbao;
    String tieude;
    String noidung;

    public ThongBao() {
    }

    public ThongBao(int mathongbao, String tieude, String noidung) {
        this.mathongbao = mathongbao;
        this.tieude = tieude;
        this.noidung = noidung;
    }

    public int getMathongbao() {
        return mathongbao;
    }

    public void setMathongbao(int mathongbao) {
        this.mathongbao = mathongbao;
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
