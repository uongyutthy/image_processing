package com.imageprocessing.controller;

import com.imageprocessing.GammaTransformation;
import com.imageprocessing.GrayScaleImage;
import com.imageprocessing.LogarithmTransformation;
import com.imageprocessing.NegativeImage;
import com.imageprocessing.utility.BrowseImage;
import com.imageprocessing.utility.BufferedImageConverter;
import com.imageprocessing.utility.KeyValueComboBox;
import com.imageprocessing.utility.PopUpMsg;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

//implement Initializable is used like form load
public class ImageProcessingController implements Initializable {

    // declare object of controller in order to map it to the view
    @FXML
    public ComboBox<KeyValueComboBox> cmbTechnique = null;

    @FXML
    public TextField txtInputImage = null;

    @FXML
    private ImageView imgOriginal = null;

    @FXML
    private ImageView imgEnhanced = null;


    public void findImage(ActionEvent actionEvent) {
        txtInputImage.setText(BrowseImage.browseImage(imgOriginal).getFilename());;

    }


    public void processImage(ActionEvent actionEvent){
        // Check if Controls are Empty
        if(cmbTechnique.getSelectionModel().isEmpty() && txtInputImage != null){

            int[][] enhancedByteArray = null;

            // Get File that has been uploaded
            File file = new File("src/com/imageprocessing/images/grayscale/" + BrowseImage.imageName); //image file path
            ImagePlus imgPlus = new ImagePlus(file.getPath());
            ImageProcessor imp = imgPlus.getProcessor();

            int width = imp.getWidth();
            int height =  imp.getHeight();

            BufferedImage bufferedImage = imgPlus.getBufferedImage();
            BufferedImageConverter bic = new BufferedImageConverter(bufferedImage, width, height);
            GrayScaleImage grayIm = new GrayScaleImage(bic.getByteArray(), BrowseImage.imageName,width, height);

            // Image Negative
            if(cmbTechnique.getValue().getKey() == 1){
                NegativeImage negImg = new NegativeImage(grayIm);
                negImg.enhance();
                enhancedByteArray = negImg.getEnhancedByteArray();

                // Logarithm Transformation
            }else if(cmbTechnique.getValue().getKey() == 2){
                LogarithmTransformation logImag = new LogarithmTransformation(grayIm);
                logImag.enhance();
                enhancedByteArray = logImag.getEnhancedByteArray();


            // Gamma Transformation
            }else if(cmbTechnique.getValue().getKey() == 3){
                GammaTransformation.GAMMA = 3.0f;
                GammaTransformation ni = new GammaTransformation(grayIm);
                ni.enhance();
                enhancedByteArray = ni.getEnhancedByteArray();
            }

            // Cover ByteArray of Image to BufferedImage
            BufferedImageConverter newBic = new BufferedImageConverter(enhancedByteArray, width, height);
            BufferedImage newBufferedImage = newBic.getBufferedImage();

            // Set Buffered Image to ImagePlace In Order To Save File Image
            ImagePlus newImgPlus = new ImagePlus();
            newImgPlus.setImage(newBufferedImage);

            // Open Location to save file
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.setFileFilter(new FileNameExtensionFilter("jpeg", "jpeg"));
            f.showSaveDialog(null);

            // Save file
            FileSaver fs = new FileSaver(imgPlus);
            fs.saveAsJpeg(f.getSelectedFile() + "\\" + BrowseImage.imageName);
            fs.saveAsJpeg("src/com/imageprocessing/images/converted/" + BrowseImage.imageName);

            // View Image to ImageViewer
            Image image = SwingFXUtils.toFXImage(newBufferedImage, null);
            imgEnhanced.setImage(image);


        }else {
            PopUpMsg.popupMsg("Alert","Please Choose Techniques And Browse Image to Convert", Alert.AlertType.INFORMATION);
            return;
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initComboBox();
        // display image in image view by default
        BrowseImage.showImage(imgOriginal, "browse-image.png");
    }


    private void initComboBox(){
        // store option of technique
        // mapped key value of combox by create new class
        ObservableList<KeyValueComboBox> options = FXCollections.observableArrayList();
        options.add(new KeyValueComboBox(1, "Image Negative"));
        options.add(new KeyValueComboBox(2, "Logarithmic  Transformation"));
        options.add(new KeyValueComboBox(3, "Gamma Transformation"));

        cmbTechnique.getItems().addAll(options);
    }
}


