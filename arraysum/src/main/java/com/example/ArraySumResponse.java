package com.example;

import task.SmartResponse;

public class ArraySumResponse implements SmartResponse {
    private float sum;

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public ArraySumResponse() {
    }

    public ArraySumResponse(float sum) {
        this.sum = sum;
    }
}
