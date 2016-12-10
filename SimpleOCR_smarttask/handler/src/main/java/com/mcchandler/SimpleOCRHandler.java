package com.mcchandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.SimpleOCR;
import com.mccfunction.SimpleOCRInput;
import com.mccfunction.SimpleOCROutput;

public class SimpleOCRHandler implements RequestHandler<SimpleOCRInput, SimpleOCROutput> {
    public SimpleOCROutput handleRequest(SimpleOCRInput request, Context context) {
        return new SimpleOCR().process(request);
    }
}

