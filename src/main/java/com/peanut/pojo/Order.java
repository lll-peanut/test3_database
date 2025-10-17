package com.peanut.pojo;

import java.time.LocalDateTime;

public class Order {
    private String id;
    private String good_id;
    private LocalDateTime time;
    private double price;

    public Order(String id, String good_id, LocalDateTime time, double price) {
        this.id = id;
        this.good_id = good_id;
        this.time = time;
        this.price = price;
    }

    public Order() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
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

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", good_id='" + good_id + '\'' +
                ", time=" + time +
                ", price=" + price +
                '}';
    }
}
