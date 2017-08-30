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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
    public ImageView imgOriginal = null;

    @FXML
    public ImageView imgEnhanced = null;

    @FXML
    public TextField txtGamma    = null;



    public void findImage(ActionEvent actionEvent) {
        GrayScaleImage img = BrowseImage.browseImage(imgOriginal);
        if(img != null) {
            txtInputImage.setText(img.getFilename());
        }

    }

    public void enableGama(){
        if(cmbTechnique.getValue().getKey() == 1 || cmbTechnique.getValue().getKey() == 2){
            txtGamma.setEditable(false);
        }else{
            txtGamma.setEditable(true);
        }
    }


    public void processImage(ActionEvent actionEvent){
        // Check if Controls are Empty
        if(!cmbTechnique.getSelectionModel().isEmpty() && txtInputImage != null){

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
                if(isNumeric(txtGamma.getText())) {
                    float gamma = Float.valueOf(txtGamma.getText());
                    GammaTransformation.GAMMA = gamma;
                    GammaTransformation ni = new GammaTransformation(grayIm);
                    ni.enhance();
                    enhancedByteArray = ni.getEnhancedByteArray();
                }else{
                    PopUpMsg.popupMsg("Alert","Please Fill In Gamma Value as Numberic", Alert.AlertType.INFORMATION);
                    return;
                }
            }

            // Cover ByteArray of Image to BufferedImage
            BufferedImageConverter newBic = new BufferedImageConverter(enhancedByteArray, width, height);
            BufferedImage newBufferedImage = newBic.getBufferedImage();

            // Set Buffered Image to ImagePlace In Order To Save File Image
            ImagePlus newImgPlus = new ImagePlus();
            newImgPlus.setImage(newBufferedImage);

            // Save file
            FileSaver fs = new FileSaver(newImgPlus);
            fs.saveAsJpeg("src/com/imageprocessing/images/converted/" + BrowseImage.imageName);

            // View Image to ImageViewer
            Image image = SwingFXUtils.toFXImage(newBufferedImage, null);
            imgEnhanced.setImage(image);

        }else {
            PopUpMsg.popupMsg("Alert","Please Choose Techniques And Browse Image to Convert", Alert.AlertType.INFORMATION);
            return;
        }


    }

    public void previewEnhancedImage(){

        if(null!= imgEnhanced.getImage()) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/src/com/imageprocessing/view/show-image.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 575, 455));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initComboBox();
        // display image in image view by default
        BrowseImage.showImage(imgOriginal, "browse-image.png");
        txtInputImage.setEditable(false);
        txtGamma.setEditable(false);
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

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

}


