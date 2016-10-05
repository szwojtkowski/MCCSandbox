package com.mccfunction;

public class ImageScalerRequest {
    private byte [] payload;
    private int height;
    private int width;
    public ImageScalerRequest(byte [] payload, int width, int height) {
        this.payload = payload;
        this.height = height;
        this.width = width;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
