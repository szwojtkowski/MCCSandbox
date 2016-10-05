package mcc.agh.edu.pl.lambdaproxy;

import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.mccfunction.BarcodeReaderRequest;
import com.mccfunction.BarcodeReaderResponse;

import task.ISharedResource;

public interface IBarcodeReaderLambdaProxy extends ISharedResource<BarcodeReaderRequest, BarcodeReaderResponse> {
    BarcodeReaderResponse process(BarcodeReaderRequest request);
}
