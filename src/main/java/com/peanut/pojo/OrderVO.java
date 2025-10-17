package com.peanut.pojo;

import java.time.LocalDateTime;

public class OrderVO {
    private String id;
    private String goodId;
    private LocalDateTime time;
    private double price;
    private String goodName;
    private double goodPrice;

    public OrderVO(String id, String goodId, LocalDateTime time, double price, String goodName, double goodPrice) {
        this.id = id;
        this.goodId = goodId;
        this.time = time;
        this.price = price;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
    }

    public OrderVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", goodId='" + goodId + '\'' +
                ", time=" + time +
                ", price=" + price +
                ", goodName='" + goodName + '\'' +
                ", goodPrice=" + goodPrice +
                '}';
    }
}
