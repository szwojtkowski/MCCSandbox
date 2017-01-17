package mcc.agh.edu.pl.lambdaproxy;

import com.mccfunction.ImageScalerInput;
import com.mccfunction.ImageScalerOutput;

import task.ISharedResource;

public interface IImageScalerLambdaProxy extends ISharedResource<ImageScalerInput, ImageScalerOutput> {
    ImageScalerOutput process(ImageScalerInput request);
}
