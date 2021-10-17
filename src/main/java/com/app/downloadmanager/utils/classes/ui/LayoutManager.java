package com.app.downloadmanager.utils.classes.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class LayoutManager {

    public Parent getDownloadManagerLayout(){
        Parent root;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layouts/DownloadManager.fxml")));
            return root;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Parent getAddDownloadLayout(){
        Parent root;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layouts/AddDownload.fxml")));
            return root;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
