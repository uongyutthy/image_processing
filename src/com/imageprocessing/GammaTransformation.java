package com.imageprocessing;

public class GammaTransformation extends GrayScaleImage {

    public GammaTransformation(int[][] byteArray, String filename, int width, int height) {
        super(byteArray, filename, width, height);
    }

    public GammaTransformation(GrayScaleImage grayIm){
        super(grayIm.getByteArray(), grayIm.getFilename(), grayIm.getWidth(), grayIm.getHeight());
    }

    public void enhance() {

    }
}
