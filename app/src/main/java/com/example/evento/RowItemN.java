package com.example.evento;


public class RowItemN {


    public String name;
    private String descr;
    public String venue;
    private String date;
    private String exp;
    public String upby;

    public RowItemN(String name, String descr, String venue, String date, String exp, String upby) {


        this.name = name;
        this.date = date;
        this.descr = descr;
        this.venue = venue;
        this.upby = upby;
        this.exp = exp;

    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getUpby() {
        return upby;
    }

    public void setUpby(String upby) {
        this.upby = upby;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}