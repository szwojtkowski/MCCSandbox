

package com.mccfunction;

import task.ISharedResource;

public interface IBarcodeReader extends ISharedResource<BarcodeReaderRequest, BarcodeReaderResponse> {
    BarcodeReaderResponse process(BarcodeReaderRequest request);
}
