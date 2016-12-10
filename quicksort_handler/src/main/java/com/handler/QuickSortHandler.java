package com.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.QuickSort;
import com.mccfunction.QuickSortInput;
import com.mccfunction.QuickSortOutput;

public class QuickSortHandler implements RequestHandler<QuickSortInput, QuickSortOutput> {
    public QuickSortOutput handleRequest(QuickSortInput request, Context context) {
        return new QuickSort().process(request);
    }
}
