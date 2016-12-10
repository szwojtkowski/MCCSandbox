package com.mccfunction;

import task.ISharedResource;

public interface ISimpleOCR extends ISharedResource<SimpleOCRInput, SimpleOCROutput> {
    SimpleOCROutput process(SimpleOCRInput request);
}
