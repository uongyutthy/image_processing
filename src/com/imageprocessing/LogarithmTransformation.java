package com.imageprocessing;

public class LogarithmTransformation extends GrayScaleImage{
    public static final double C = (255/(Math.log(1+255)));

    public LogarithmTransformation(int[][] byteArray, String filename, int width, int height) {
        super(byteArray, filename, width, height);
    }

    public LogarithmTransformation(GrayScaleImage grayIm){
        super(grayIm.getByteArray(), grayIm.getFilename(), grayIm.getWidth(), grayIm.getHeight());
    }

    public void enhance() {

        this.enhancedByteArray = new int[this.height][this.width];
        for (int x = 0; x < this.getHeight(); x++) {
            for (int y = 0; y < this.getWidth(); y++) {
                this.enhancedByteArray[x][y] = (int) ( C * (Math.log(1 + this.byteArray[x][y])));
            }
        }
    }
}
