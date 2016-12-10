package com.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.ArraySum;
import com.example.ArraySumInput;
import com.example.ArraySumOutput;

public class ArraySumLambdaHandler implements RequestHandler<ArraySumInput, ArraySumOutput> {

    public ArraySumOutput handleRequest(ArraySumInput request, Context context) {
        return new ArraySum().process(request);
    }
}
