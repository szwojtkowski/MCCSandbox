package com.example;

public class ArraySum implements IArraySum {

    @Override
    public ArraySumResponse process(ArraySumRequest request) {
        float sum = 0;
        for (float value: request.getArray()) {
            sum += value;
        }
        return new ArraySumResponse(sum / request.getArray().length);
    }
}
