package com.imageprocessing.utility;


import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class BufferedImageConverter {
    protected BufferedImage bufferedImage = null;
    protected int[][] byteArray = null;
    protected int width = 0;
    protected int height = 0;

    public BufferedImageConverter(BufferedImage bufferedImage, int width, int height) {
        this.bufferedImage = bufferedImage;
        this.width = width;
        this.height = height;
    }

    public BufferedImageConverter(int[][] byteArray, int width, int height) {
        this.byteArray = byteArray;
        this.width = width;
        this.height = height;
    }

    public BufferedImage getBufferedImage() {
        if(this.bufferedImage == null){
            return this.toBufferedImage();
        }
        return this.bufferedImage;
    }


    public int[][] getByteArray() {
        if(this.byteArray == null){
            return this.toByteArray();
        }
        return this.byteArray;
    }

    private BufferedImage toBufferedImage(){
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.bufferedImage.getRaster().setSample(x, y, 0, this.byteArray[y][x]) ;
            }
        }
        return this.bufferedImage;
    }

    private int[][] toByteArray(){
        this.byteArray = new int[this.height][this.width];
        Raster raster = this.bufferedImage.getData();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.byteArray[y][x] = raster.getSample(x, y, 0);
            }
        }

        return this.byteArray;
    }
}