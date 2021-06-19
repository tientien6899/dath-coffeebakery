package com.example.coffeebakery.DetailReceipt;

public class DetailReceipt {
    String link, sl, ten, gia;

    public DetailReceipt() {
    }

    public DetailReceipt(String link, String sl, String ten, String gia) {
        this.link = link;
        this.sl = sl;
        this.ten = ten;
        this.gia = gia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
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
}
