package com.mccfunction;

import task.SmartResponse;

public class SimpleOCRResponse implements SmartResponse {
    private String text;
    public SimpleOCRResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
