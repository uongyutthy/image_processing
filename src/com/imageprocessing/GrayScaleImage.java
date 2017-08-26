package com.imageprocessing;

public class GrayScaleImage {
    protected String filename = null;
    protected int[][] byteArray = null;
    protected int width = 0;
    protected int height = 0;

    public GrayScaleImage(int[][] byteArray, String filename, int width, int height){
        this.byteArray = byteArray;
        this.filename = filename;
        this.width = width;
        this.height = height;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int[][] getByteArray() {
        return byteArray;
    }

    public void setByteArray(int[][] byteArray) {
        this.byteArray = byteArray;
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
