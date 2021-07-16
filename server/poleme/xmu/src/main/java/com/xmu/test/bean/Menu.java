package com.xmu.test.bean;

public class Menu {
    private int id;
    private String name;
    private int stock;
    private String style;
    private String imgurl;
    private double price;
    private int proceeds;
    private boolean state;
    private String description;
    private String path;

    public Menu() {
    }

    public Menu(int id, String name, int stock, String style, String imgurl, double price, int proceeds, boolean state, String description, String path) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.style = style;
        this.imgurl = imgurl;
        this.price = price;
        this.proceeds = proceeds;
        this.state = state;
        this.description = description;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProceeds() {
        return proceeds;
    }

    public void setProceeds(int proceeds) {
        this.proceeds = proceeds;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", style='" + style + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", price=" + price +
                ", proceeds=" + proceeds +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
