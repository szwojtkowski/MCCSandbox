package mcc.agh.edu.pl.lambdaproxy;

import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.mccfunction.ImageScalerRequest;
import com.mccfunction.ImageScalerResponse;

import task.ISharedResource;

public interface IImageScalerLambdaProxy extends ISharedResource<ImageScalerRequest, ImageScalerResponse> {
    ImageScalerResponse process(ImageScalerRequest request);
}
