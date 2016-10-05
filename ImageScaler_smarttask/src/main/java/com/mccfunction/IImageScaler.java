package com.mccfunction;

import task.ISharedResource;

public interface IImageScaler extends ISharedResource<ImageScalerRequest, ImageScalerResponse> {
    ImageScalerResponse process(ImageScalerRequest request);
}
