package com.mccfunction;

import task.SmartRequest;

public class QuickSortRequest implements SmartRequest{
    double [] inputArray;
    public QuickSortRequest(double [] arr) {
        inputArray = arr;
    }

    double [] getInputArray() {
        return this.inputArray;
    }
}
