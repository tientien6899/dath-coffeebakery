package com.example.coffeebakery.Setting;

public class Profile {

    public String hoten;
    public String sdt;
    public String sonha;
    public String gmail;
    public String userid;
    public String avatar;

    public Profile() {

    }

    public Profile(String hoten, String sdt, String sonha, String ava, String gmail, String ud) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.sonha = sonha;
        this.avatar = ava;
        this.gmail = gmail;
        this.userid = ud;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSonha(String sonha) {
        this.sonha = sonha;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getUID() {
        return userid;
    }

    public void setUID(String ud) {
        this.userid = ud;
    }
}
