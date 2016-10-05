

package com.mccfunction;

import task.SmartResponse;

public class QuickSortResponse implements SmartResponse{
    private double [] outputArray;
    public QuickSortResponse(double[] outputArray) {
        this.outputArray = outputArray;
    }

    public double[] getOutputArray() {
        return outputArray;
    }

    public void setOutputArray(double[] outputArray) {
        this.outputArray = outputArray;
    }
}
