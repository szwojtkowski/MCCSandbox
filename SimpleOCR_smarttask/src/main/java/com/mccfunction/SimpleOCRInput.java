package com.mccfunction;

import task.SmartInput;

public class SimpleOCRInput implements SmartInput {
    private byte [] payload;
    private OCRLang language;
    public SimpleOCRInput(byte [] payload, OCRLang lang) {
        this.payload = payload;
        this.language = lang;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public OCRLang getLanguage() {
        return language;
    }

    public void setLanguage(OCRLang language) {
        this.language = language;
    }
}
