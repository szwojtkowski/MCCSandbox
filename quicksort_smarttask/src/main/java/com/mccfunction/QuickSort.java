

package com.mccfunction;

public class QuickSort implements IQuickSort {

    @Override
    public QuickSortResponse process(QuickSortRequest request) {
        double [] array = request.getInputArray();
        java.util.Arrays.sort(array);
        return new QuickSortResponse(array);
    }
}

