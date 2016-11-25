package com.example;

import task.SmartResponse;

public class ArraySumResponse implements SmartResponse {
    private double sum;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ArraySumResponse() {
    }

    public ArraySumResponse(double sum) {
        this.sum = sum;
    }
}
