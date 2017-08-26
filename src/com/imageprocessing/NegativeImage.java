package com.imageprocessing;

public class NegativeImage extends GrayScaleImage {

    public static final int MAX_BITS = 256;

    public NegativeImage(int[][] byteArray, String filename, int width, int height) {
        super(byteArray, filename, width, height);
    }

    public NegativeImage(GrayScaleImage grayIm){
        super(grayIm.getByteArray(), grayIm.getFilename(), grayIm.getWidth(), grayIm.getHeight());
    }

    public void enhance() {

        this.enhancedByteArray = new int[this.height][this.width];
        for(int x = 0; x < this.getHeight(); x++){
            for(int y = 0; y < this.getWidth(); y++){
                this.enhancedByteArray[x][y] = (MAX_BITS - 1) - this.byteArray[x][y];
            }
        }

    }
}
