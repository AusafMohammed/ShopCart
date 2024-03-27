package com.example.shopcart;

public class coupons {
    String code,sponsor,discount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public coupons() {
    }

    public coupons(String code, String sponsor, String discount) {
        this.code = code;
        this.sponsor = sponsor;
        this.discount = discount;
    }
}
