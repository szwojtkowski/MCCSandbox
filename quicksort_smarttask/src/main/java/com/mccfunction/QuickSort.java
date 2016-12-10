

package com.mccfunction;

public class QuickSort implements IQuickSort {

    @Override
    public QuickSortOutput process(QuickSortInput request) {
        double [] array = request.getInputArray();
        java.util.Arrays.sort(array);
        return new QuickSortOutput(array);
    }
}

