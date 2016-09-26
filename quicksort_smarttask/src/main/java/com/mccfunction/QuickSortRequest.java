package com.mccfunction;

public class QuickSortRequest {
    double [] inputArray;
    public QuickSortRequest(double [] arr) {
        inputArray = arr;
    }

    double [] getInputArray() {
        return this.inputArray;
    }
}
