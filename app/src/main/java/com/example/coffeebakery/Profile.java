package com.example.coffeebakery;

public class Profile {

    public String hoten;
    public String sdt;
    public String sonha;
    public String phuong;
    public String quan;
    public String thanhpho;
    public String gmail;

    public Profile() {

    }

    public Profile(String hoten, String sdt, String sonha, String phuong, String quan, String thanhpho, String gmail) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.sonha = sonha;
        this.phuong = phuong;
        this.quan = quan;
        this.thanhpho = thanhpho;
        this.gmail = gmail;
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

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        this.thanhpho = thanhpho;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
