package com.imageprocessing.controller;

import com.imageprocessing.utility.BrowseImage;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewEnhancedImageController implements Initializable {

    @FXML
    public ImageView previewImage = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("images/converted/" + BrowseImage.imageName);
        previewImage.setImage(image);
    }

    public void saveImage(){
        // Open Location to save file
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.setFileFilter(new FileNameExtensionFilter("jpeg", "jpeg"));
        f.showSaveDialog(null);

        File file = new File("images/converted/" + BrowseImage.imageName); //image file path
        ImagePlus imgPlus = new ImagePlus(file.getPath());

        if(f.getSelectedFile() != null) {
            // Save file
            FileSaver fs = new FileSaver(imgPlus);
            fs.saveAsJpeg(f.getSelectedFile() + "\\" + BrowseImage.imageName);

        }
    }



}
