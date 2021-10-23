package com.app.downloadmanager.utils.classes.ui;

import com.app.downloadmanager.controller.DownloadManagerController;
import com.app.downloadmanager.model.File;
import com.app.downloadmanager.utils.classes.core.DatabaseHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserInterface {

    private static LayoutManager layoutManager;

    public static Stage getDownloadManagerStage(){
        if (layoutManager == null)
            layoutManager = new LayoutManager();
        Stage stage = new Stage();
        stage.setScene(new Scene(layoutManager.getDownloadManagerLayout()));
        stage.setResizable(false);
        stage.setTitle("Download Manager");
        stage.setOnCloseRequest(event -> {
            for(File file : DownloadManagerController.tableAllFiles){
                DatabaseHandler.updateDownloadSizeAndProgress(file);
            }
        });
        return stage;
    }

    public static Stage getAddDownloadStage(){
        if (layoutManager == null)
            layoutManager = new LayoutManager();
        Stage stage = new Stage();
        stage.setScene(new Scene(layoutManager.getAddDownloadLayout()));
        stage.setResizable(false);
        stage.setTitle("Add a download");
        return stage;
    }

}
