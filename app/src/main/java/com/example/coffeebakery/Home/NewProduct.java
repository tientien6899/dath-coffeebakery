package com.example.coffeebakery.Home;

public class NewProduct {
    String masp;
    String tensp;
    String giasp;

    String link;
    String ngaydang;

    public NewProduct() {
    }

    public NewProduct(String masp, String tensp, String giasp, String link, String ngaydang) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.link = link;
        this.ngaydang = ngaydang;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }
}
