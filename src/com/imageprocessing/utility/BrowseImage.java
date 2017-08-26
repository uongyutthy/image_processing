package com.imageprocessing.utility;

import ij.CompositeImage;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import javafx.scene.image.PixelReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.imageprocessing.GrayScaleImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

public class BrowseImage {

    public static String imageName = null;

    public static GrayScaleImage browseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        GrayScaleImage grayIm = null;

        if (file!= null) {
            imageName = file.getName();
            try{
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);


                ImagePlus imgPlus = new ImagePlus();
                imgPlus.setImage(bufferedImage);
                ImageProcessor imp = imgPlus.getProcessor();

                if(!imp.isGrayscale()){
                    System.out.println("test");
                    imgPlus.setDisplayMode(CompositeImage.GRAY8);
                    bufferedImage = imgPlus.getBufferedImage();
                }

                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();

                BufferedImageConverter bic = new BufferedImageConverter(bufferedImage, width, height);
                grayIm = new GrayScaleImage(bic.getByteArray(), imageName,width, height);
                FileSaver fs = new FileSaver(imgPlus);
                fs.saveAsJpeg("src/com/imageprocessing/images/grayscale/" + imageName);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return grayIm;
    }

    public static void showImage(ImageView imageView, String fileName) {
        Image image = new Image("/com/imageprocessing/images/" + fileName);
        imageView.setImage(image);
    }


}


