package com.peanut.pojo;

public class Good {
    private String id;
    private String name;
    private double price;
    private int isDeleted;

    public Good(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Good(String id, String name, double price, int isDeleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDeleted = isDeleted;
    }

    public Good(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public int isDeleted() {
        return isDeleted;
    }

    public void setDeleted(int deleted) {
        isDeleted = deleted;
    }

    public Good(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
