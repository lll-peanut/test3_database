package com.peanut.pojo;

import java.time.LocalDateTime;

public class Order {
    private String id;
    private LocalDateTime time;
    private double price;

    public Order(String id, LocalDateTime time, double price) {
        this.id = id;
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
                ", time=" + time +
                ", price=" + price +
                '}';
    }
}
