package com.xmu.test.bean;

import java.util.List;

public class Orders {
    private int orderid;
    private int menuid;
    private int userid;
    private String date;
    private int unitnum;
    private double unitprice;
    private double price;
    private List<OrderDetails> detailslist;

    public Orders() {
    }

    public Orders(int orderid, int menuid, int userid, String date, int unitnum, double unitprice, double price) {
        this.orderid = orderid;
        this.menuid = menuid;
        this.userid = userid;
        this.date = date;
        this.unitnum = unitnum;
        this.unitprice = unitprice;
        this.price = price;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(int unitnum) {
        this.unitnum = unitnum;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderDetails> getDetailslist() {
        return detailslist;
    }

    public void setDetailslist(List<OrderDetails> detailslist) {
        this.detailslist = detailslist;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderid=" + orderid +
                ", menuid=" + menuid +
                ", userid=" + userid +
                ", date='" + date + '\'' +
                ", unitnum=" + unitnum +
                ", unitprice=" + unitprice +
                ", price=" + price +
                ", detailslist=" + detailslist +
                '}';
    }
}
