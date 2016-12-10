

package com.mccfunction;

import task.SmartOutput;

public class QuickSortOutput implements SmartOutput {
    private double [] outputArray;
    public QuickSortOutput(double[] outputArray) {
        this.outputArray = outputArray;
    }

    public double[] getOutputArray() {
        return outputArray;
    }

    public void setOutputArray(double[] outputArray) {
        this.outputArray = outputArray;
    }
}
