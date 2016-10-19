package com.mcchandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.PolymonialHalt;
import com.mccfunction.PolymonialHaltRequest;
import com.mccfunction.PolymonialHaltResponse;

public class PolymonialHaltHandler implements RequestHandler<PolymonialHaltRequest, PolymonialHaltResponse> {
    public PolymonialHaltResponse handleRequest(PolymonialHaltRequest request, Context context) {
        return new PolymonialHalt().process(request);
    }
}

