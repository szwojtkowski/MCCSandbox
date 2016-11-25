package com.example;

public class ArraySum implements IArraySum {

    @Override
    public ArraySumResponse process(ArraySumRequest request) {
        double sum = 0;
        for (double value: request.getArray()) {
            sum += value;
        }
        return new ArraySumResponse(sum / request.getArray().length);
    }
}
