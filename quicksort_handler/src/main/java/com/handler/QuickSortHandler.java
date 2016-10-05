package com.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.QuickSort;
import com.mccfunction.QuickSortRequest;
import com.mccfunction.QuickSortResponse;

public class QuickSortHandler implements RequestHandler<QuickSortRequest, QuickSortResponse> {
    public QuickSortResponse handleRequest(QuickSortRequest request, Context context) {
        return new QuickSort().process(request);
    }
}
