package mcc.agh.edu.pl.lambdaproxy;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.example.ArraySumRequest;
import com.example.ArraySumResponse;
import com.example.IArraySum;

import task.ISharedResource;

public interface IArraySumLambdaProxy extends IArraySum {
    @LambdaFunction(functionName="ArraySum")
    ArraySumResponse process(ArraySumRequest request);
}
