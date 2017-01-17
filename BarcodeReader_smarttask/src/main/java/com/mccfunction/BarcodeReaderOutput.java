package com.mccfunction;

import task.SmartOutput;

public class BarcodeReaderOutput implements SmartOutput {
    private String response;

    public BarcodeReaderOutput(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
