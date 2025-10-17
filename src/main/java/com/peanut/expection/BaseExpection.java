package com.peanut.expection;

public class BaseExpection extends RuntimeException {
    public BaseExpection(String message) {
        super(message);
    }
}
