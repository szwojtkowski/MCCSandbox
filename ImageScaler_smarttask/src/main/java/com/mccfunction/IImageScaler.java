package com.mccfunction;

import task.ISharedResource;

public interface IImageScaler extends ISharedResource<ImageScalerInput, ImageScalerOutput> {
    ImageScalerOutput process(ImageScalerInput request);
}
