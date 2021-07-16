package com.xmu.test.bean;

public class ShoppingCart {
    private int userid;
    private int menuid;
    private int num;
    private String selected;

    public ShoppingCart() {
    }

    public ShoppingCart(int userid, int menuid, int num, String selected) {
        this.userid = userid;
        this.menuid = menuid;
        this.num = num;
        this.selected = selected;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "userid=" + userid +
                ", menuid=" + menuid +
                ", num=" + num +
                ", selected='" + selected + '\'' +
                '}';
    }
}
