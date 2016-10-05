package mcc.agh.edu.pl.lambdaproxy;

import com.example.ArraySumRequest;
import com.example.ArraySumResponse;

import task.ISharedResource;

public interface IArraySumLambdaProxy extends ISharedResource<ArraySumRequest, ArraySumResponse> {
    ArraySumResponse process(ArraySumRequest request);
}
