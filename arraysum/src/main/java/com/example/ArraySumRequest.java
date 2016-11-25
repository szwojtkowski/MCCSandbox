package com.example;

import task.SmartRequest;

public class ArraySumRequest implements SmartRequest{
    private double[] array;


    public ArraySumRequest(double[] array) {
        this.array = array;
    }

    public ArraySumRequest() {
    }

    public double[] getArray() {
        return array;
    }

    public void setArray(double[] array) {
        this.array = array;
    }
}
