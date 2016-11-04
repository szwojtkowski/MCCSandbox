package com.mcchandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.SimpleOCR;
import com.mccfunction.SimpleOCRRequest;
import com.mccfunction.SimpleOCRResponse;

public class SimpleOCRHandler implements RequestHandler<SimpleOCRRequest, SimpleOCRResponse> {
    public SimpleOCRResponse handleRequest(SimpleOCRRequest request, Context context) {
        return new SimpleOCR().process(request);
    }
}

