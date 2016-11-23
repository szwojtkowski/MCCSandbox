package com.mccfunction;

import task.SmartResponse;

public class BarcodeReaderResponse implements SmartResponse{
    private String response;

    public BarcodeReaderResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
