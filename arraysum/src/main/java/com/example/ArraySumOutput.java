package com.example;

import task.SmartOutput;

public class ArraySumOutput implements SmartOutput {
    private double sum;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ArraySumOutput() {
    }

    public ArraySumOutput(double sum) {
        this.sum = sum;
    }
}
