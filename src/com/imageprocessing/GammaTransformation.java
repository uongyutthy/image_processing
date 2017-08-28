package com.imageprocessing;

public class GammaTransformation extends GrayScaleImage {

    public static final int C = 1;
    public static float GAMMA = 3.0f;

    public GammaTransformation(int[][] byteArray, String filename, int width, int height) {
        super(byteArray, filename, width, height);
    }

    public GammaTransformation(GrayScaleImage grayIm){
        super(grayIm.getByteArray(), grayIm.getFilename(), grayIm.getWidth(), grayIm.getHeight());
    }

    public void enhance() {
        this.enhancedByteArray = new int[this.height][this.width];
        for (int x = 0; x < this.getHeight(); x++) {
            for (int y = 0; y < this.getWidth(); y++) {
                this.enhancedByteArray[x][y] = (int) (C * 255 * Math.pow((float)this.byteArray[x][y]/255,GAMMA));
            }
        }
    }
}

