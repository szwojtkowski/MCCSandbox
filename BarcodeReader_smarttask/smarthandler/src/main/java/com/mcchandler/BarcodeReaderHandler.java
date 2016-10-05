package com.mcchandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.BarcodeReader;
import com.mccfunction.BarcodeReaderRequest;
import com.mccfunction.BarcodeReaderResponse;

public class BarcodeReaderHandler implements RequestHandler<BarcodeReaderRequest, BarcodeReaderResponse> {
    public BarcodeReaderResponse handleRequest(BarcodeReaderRequest request, Context context) {
        return new BarcodeReader().process(request);
    }
}

