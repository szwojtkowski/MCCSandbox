package com.mccfunction;

import task.ISharedResource;

public interface IBarcodeReader extends ISharedResource<BarcodeReaderInput, BarcodeReaderOutput> {
    BarcodeReaderOutput process(BarcodeReaderInput request);
}
