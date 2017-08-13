package com.imageprocessing.controller;

import com.imageprocessing.utility.BrowseImage;
import com.imageprocessing.utility.KeyValueComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
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


    public void findImage(ActionEvent actionEvent) {
        txtInputImage.setText(BrowseImage.browseImage(imgOriginal));;

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
        options.add(new KeyValueComboBox(1, "Technique 1"));
        options.add(new KeyValueComboBox(2, "Technique 2"));
        options.add(new KeyValueComboBox(3, "Technique 3"));

        cmbTechnique.getItems().addAll(options);
    }
}


