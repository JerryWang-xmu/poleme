package com.xmu.test.bean;

public class OrderDetails {
    private int orderid;
    private int menuid;
    private int unitnum;
    private String menuname;
    private double unitprice;
    private String imgurl;

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public OrderDetails(int orderid, int menuid, int unitnum, double unitprice, String imgurl) {
        this.orderid = orderid;
        this.menuid = menuid;
        this.unitnum = unitnum;
        this.unitprice = unitprice;
        this.imgurl = imgurl;
    }

    public OrderDetails() {
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderid=" + orderid +
                ", menuid=" + menuid +
                ", unitnum=" + unitnum +
                ", menuname='" + menuname + '\'' +
                ", unitprice=" + unitprice +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
