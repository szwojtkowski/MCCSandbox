package com.example;

import task.ISharedResource;

public interface IArraySum extends ISharedResource<ArraySumRequest, ArraySumResponse> {
    ArraySumResponse process(ArraySumRequest request);
}
