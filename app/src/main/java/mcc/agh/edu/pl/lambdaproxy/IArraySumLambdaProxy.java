package mcc.agh.edu.pl.lambdaproxy;

import com.example.ArraySumInput;
import com.example.ArraySumOutput;

import task.ISharedResource;

public interface IArraySumLambdaProxy extends ISharedResource<ArraySumInput, ArraySumOutput> {
    ArraySumOutput process(ArraySumInput request);
}
