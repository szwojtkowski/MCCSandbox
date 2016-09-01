package com.example;

public class ArraySumRequest {
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
