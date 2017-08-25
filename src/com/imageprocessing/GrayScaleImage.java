package com.imageprocessing;
import ij.ImagePlus;
public class GrayScaleImage {
    protected String filename = null;
    protected ImagePlus grayScaleImagePlus = null;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {

        return filename;
    }

    public ImagePlus getGrayScaleImagePlus() {
        return grayScaleImagePlus;
    }

    public void setGrayScaleImagePlus(ImagePlus grayScaleImagePlus) {
        this.grayScaleImagePlus = grayScaleImagePlus;
    }
}
