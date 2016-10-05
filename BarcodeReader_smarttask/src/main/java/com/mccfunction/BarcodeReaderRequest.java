package com.mccfunction;

import task.SmartRequest;

public class BarcodeReaderRequest implements SmartRequest {
    private int[] pixels;
    private int width;
    private int height;

    public BarcodeReaderRequest(int [] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
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
