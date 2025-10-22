package com.peanut.pojo;

import java.time.LocalDateTime;
import java.util.List;

public class OrderVO {
    private String id;
    private LocalDateTime time;
    private double price;
    List<GoodInfo> goodInfo;

    public OrderVO(String id, LocalDateTime time, double price, List<GoodInfo> goodInfo) {
        this.id = id;
        this.time = time;
        this.price = price;
        this.goodInfo = goodInfo;
    }

    public OrderVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<GoodInfo> getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(List<GoodInfo> goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", price=" + price +
                ", goodInfo=" + goodInfo +
                '}';
    }
}
