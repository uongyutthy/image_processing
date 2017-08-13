package com.imageprocessing.utility;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BrowseImage {

    public static String imageName = null;

    public static String browseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        String fileName = "";
        if (file!= null) {
            fileName = file.getName();
            imageName = fileName;
            try {

                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
                ImagePlus imgPlus = new ImagePlus(file.getPath());
                ImageProcessor imgProcessor = imgPlus.getProcessor();
                //imgProcessor.invert();
                //FileSaver fs = new FileSaver(imgPlus);
                //fs.saveAsJpeg("src/com/imageprocessing/images/grayscale/" + fileName);

                BufferedImage bufferedImages = imgProcessor.getBufferedImage();
                for(int y=0;y<bufferedImages.getHeight();y++)
                {
                    for(int x=0;x<bufferedImages.getWidth();x++)
                    {
                        Color color = new Color(bufferedImages.getRGB(x, y));
                        int grayLevel = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                        int r = grayLevel;
                        int g = grayLevel;
                        int b = grayLevel;
                        int rgb = (r<<16)  | (g<<8)  | b;
                        bufferedImage.setRGB(x, y, rgb);
                    }
                }
                ImagePlus grayImg = new ImagePlus("gray", bufferedImage);
                FileSaver fs = new FileSaver(grayImg);
                fs.saveAsJpeg("src/com/imageprocessing/images/grayscale/" + fileName);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return fileName;
    }

    public static void showImage(ImageView imageView, String fileName) {
        Image image = new Image("/com/imageprocessing/images/" + fileName);
        imageView.setImage(image);
    }


}


