package com.app.downloadmanager.utils.classes;

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
        return stage;
    }

}
