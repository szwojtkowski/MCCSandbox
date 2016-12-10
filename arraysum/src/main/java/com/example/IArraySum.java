package com.example;

import task.ISharedResource;

public interface IArraySum extends ISharedResource<ArraySumInput, ArraySumOutput> {
    ArraySumOutput process(ArraySumInput request);
}
