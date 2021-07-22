package com.example.coffeebakery;

public class Remember {
    String tendangnhap;
    String userid;
    String mac;

    public Remember() {
    }
    public Remember(String tendangnhap, String userid, String mac) {
        this.tendangnhap = tendangnhap;
        this.userid = userid;
        this.mac = mac;
    }



    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMAC() {
        return mac;
    }

    public void setMAC(String mac) {
        this.mac = mac;
    }
}
