package com.imageprocessing.controller;

import com.imageprocessing.utility.BrowseImage;
import ij.ImagePlus;
import ij.io.FileSaver;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewEnhancedImageController implements Initializable {

    @FXML
    public ImageView previewImage = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = SwingFXUtils.toFXImage(ImageProcessingController.newBufferedImage, null);
        previewImage.setImage(image);
    }

    public void saveImage(){
        // Open Location to save file
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.setFileFilter(new FileNameExtensionFilter("jpeg", "jpeg"));
        f.showSaveDialog(null);

        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(ImageProcessingController.newBufferedImage);

        if(f.getSelectedFile() != null) {
            // Save file
            FileSaver fs = new FileSaver(imagePlus);
            fs.saveAsJpeg(f.getSelectedFile() + "\\" + BrowseImage.imageName);

        }
    }



}
