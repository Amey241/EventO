package com.example.evento;


public class RowItem {


    private String name;
    private String type;
    private String pass;

    public RowItem(String name, String type, String pass) {


        this.name = name;
        this.type = type;
        this.pass = pass;

    }

    public String getPass() {
        return pass;
    }

    public String getType() {
        return type;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}