package com.mccfunction;

public class ImageScalerResponse {
    private final int height;
    private final int width;
    private byte [] payload;

    public ImageScalerResponse(byte [] payload, int width, int height) {
        this.payload = payload;
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
