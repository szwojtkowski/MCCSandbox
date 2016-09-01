package com.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.ArraySum;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;

public class ArraySumLambdaHandler implements RequestHandler<ArraySumRequest, ArraySumResponse> {

    public ArraySumResponse handleRequest(ArraySumRequest request, Context context) {
        return new ArraySum().process(request);
    }
}
