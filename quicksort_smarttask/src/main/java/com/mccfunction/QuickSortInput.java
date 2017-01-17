package com.mccfunction;

import task.SmartInput;

public class QuickSortInput implements SmartInput {
    double [] inputArray;
    public QuickSortInput(double [] arr) {
        inputArray = arr;
    }

    double [] getInputArray() {
        return this.inputArray;
    }
}
