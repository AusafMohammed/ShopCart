package com.example.shopcart;

public class visacard {
    int cardnumber,month,year,cvv;
    String cardname,user,who;

    public visacard() {
    }

    public int getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public visacard(int cardnumber, int month, int year, int cvv, String cardname, String user, String who) {
        this.cardnumber = cardnumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        this.cardname = cardname;
        this.user=user;
        this.who=who;
    }
}
