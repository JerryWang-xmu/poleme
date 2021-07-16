package com.xmu.test.bean;

public class Rank {
    int order;
    int id;
    String name;
    float price;
    String imgurl;

    public Rank() {
    }

    public Rank(int order, int id, String name, float price, String imgurl) {
        this.order = order;
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgurl = imgurl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "order=" + order +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
