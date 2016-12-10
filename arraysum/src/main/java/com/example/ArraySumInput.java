package com.example;

import task.SmartInput;

public class ArraySumInput implements SmartInput {
    private double[] array;


    public ArraySumInput(double[] array) {
        this.array = array;
    }

    public ArraySumInput() {
    }

    public double[] getArray() {
        return array;
    }

    public void setArray(double[] array) {
        this.array = array;
    }
}
