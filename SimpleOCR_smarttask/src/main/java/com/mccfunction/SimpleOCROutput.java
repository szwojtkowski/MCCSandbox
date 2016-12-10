package com.mccfunction;

import task.SmartOutput;

public class SimpleOCROutput implements SmartOutput {
    private String text;
    public SimpleOCROutput(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
