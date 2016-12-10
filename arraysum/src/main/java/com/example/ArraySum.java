package com.example;

public class ArraySum implements IArraySum {

    @Override
    public ArraySumOutput process(ArraySumInput request) {
        double sum = 0;
        for (double value: request.getArray()) {
            sum += value;
        }
        return new ArraySumOutput(sum / request.getArray().length);
    }
}
