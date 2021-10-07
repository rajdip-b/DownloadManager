package com.app.downloadmanager.utils.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class LayoutManager {

    public Parent getDownloadManagerLayout(){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layouts/DownloadManager.fxml")));
            return root;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
