package com.app.downloadmanager;

import com.app.downloadmanager.utils.classes.AppProperties;
import com.app.downloadmanager.utils.classes.Keys;
import com.app.downloadmanager.utils.classes.TableBarMenu;
import com.app.downloadmanager.utils.classes.UserInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {

    public static void main(String[] args) {
        if (!propertyFileExists())
            createPropertyFile();
        readAndSetProperties();
        launch(args);
    }

    private static boolean propertyFileExists(){
        File file = new File(Keys.APP_CONFIG_FILE_NAME);
        return file.exists();
    }

    private static void createPropertyFile(){
        File file = new File(Keys.APP_CONFIG_FILE_NAME);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.format("%s=%s\n", Keys.DEFAULT_SAVE_LOCATION_KEY, System.getenv("HOME")));
            fileWriter.write(String.format("%s=%s\n", Keys.DEFAULT_PACKET_SIZE_KEY, 1024));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't create "+ Keys.APP_CONFIG_FILE_NAME);
            System.exit(1);
        }
    }

    private static void readAndSetProperties(){
        try{
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(Keys.APP_CONFIG_FILE_NAME);
            properties.load(fileInputStream);
            AppProperties.DEFAULT_SAVE_LOCATION = properties.getProperty(Keys.DEFAULT_SAVE_LOCATION_KEY);
            AppProperties.DEFAULT_PACKET_SIZE = Integer.parseInt(properties.getProperty(Keys.DEFAULT_PACKET_SIZE_KEY));
        }catch (IOException e){
            System.out.println("Error reading property file!");
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserInterface.getDownloadManagerStage().show();
    }
}
