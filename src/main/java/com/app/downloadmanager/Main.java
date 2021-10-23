package com.app.downloadmanager;

import com.app.downloadmanager.utils.classes.core.AppProperties;
import com.app.downloadmanager.utils.classes.core.DatabaseHandler;
import com.app.downloadmanager.utils.classes.core.Keys;
import com.app.downloadmanager.utils.classes.core.PropertyManager;
import com.app.downloadmanager.utils.classes.ui.UserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import java.sql.*;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {
        if (!PropertyManager.propertyFileExists())
            PropertyManager.createPropertyFile();
        PropertyManager.readAndSetProperties();
        if (!DatabaseHandler.checkConnection())
            DatabaseHandler.createDatabase();
        launch(args);
        System.exit(1);
    }

    @Override
    public void start(Stage primaryStage) {
        UserInterface.getDownloadManagerStage().show();
    }
}
