package com.mccfunction;

import task.ISharedResource;

public interface ISimpleOCR extends ISharedResource<SimpleOCRRequest, SimpleOCRResponse> {
    SimpleOCRResponse process(SimpleOCRRequest request);
}
