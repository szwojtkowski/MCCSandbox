package mcc.agh.edu.pl.lambdaproxy;

import com.mccfunction.BarcodeReaderInput;
import com.mccfunction.BarcodeReaderOutput;

import task.ISharedResource;

public interface IBarcodeReaderLambdaProxy extends ISharedResource<BarcodeReaderInput, BarcodeReaderOutput> {
    BarcodeReaderOutput process(BarcodeReaderInput request);
}
