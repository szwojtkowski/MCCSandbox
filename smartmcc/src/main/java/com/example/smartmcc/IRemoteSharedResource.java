package com.example.smartmcc;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

import task.ISharedResource;

public interface IRemoteSharedResource <Q, S> extends ISharedResource <Q, S> {
    @LambdaFunction
    public S process (Q request);
}
