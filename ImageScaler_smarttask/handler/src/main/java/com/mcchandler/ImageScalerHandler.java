package com.mcchandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mccfunction.ImageScaler;
import com.mccfunction.ImageScalerRequest;
import com.mccfunction.ImageScalerResponse;

public class ImageScalerHandler implements RequestHandler<ImageScalerRequest, ImageScalerResponse> {
    public ImageScalerResponse handleRequest(ImageScalerRequest request, Context context) {
        return new ImageScaler().process(request);
    }
}

