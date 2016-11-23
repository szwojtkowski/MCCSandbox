package com.example;

import task.SmartRequest;

public class ArraySumRequest implements SmartRequest{
    private float[] array;


    public ArraySumRequest(float[] array) {
        this.array = array;
    }

    public ArraySumRequest() {
    }

    public float[] getArray() {
        return array;
    }

    public void setArray(float[] array) {
        this.array = array;
    }
}
