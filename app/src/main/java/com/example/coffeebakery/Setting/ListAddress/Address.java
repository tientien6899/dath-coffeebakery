package com.example.coffeebakery.Setting.ListAddress;

public class Address {
    String uid;
    String hoten;
    String sdt;
    String sonha;

    public Address(String uid, String hoten, String sdt, String sonha) {
        this.uid = uid;
        this.hoten = hoten;
        this.sdt = sdt;
        this.sonha = sonha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
