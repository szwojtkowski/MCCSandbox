

package com.mccfunction;

public class BarcodeReader implements IBarcodeReader {

    @Override
    public BarcodeReaderResponse process(BarcodeReaderRequest request) {
        return new BarcodeReaderResponse();
    }
}

