package com.app.downloadmanager.utils.classes.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    public static boolean propertyFileExists(){
        File file = new File(Keys.APP_CONFIG_FILE_NAME);
        return file.exists();
    }

    public static void createPropertyFile(){
        File file = new File(Keys.APP_CONFIG_FILE_NAME);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.format("%s=%s\n", Keys.DEFAULT_SAVE_LOCATION_KEY, System.getProperty("user.home")));
            fileWriter.write(String.format("%s=%s\n", Keys.DEFAULT_PACKET_SIZE_KEY, 1024));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't create "+ Keys.APP_CONFIG_FILE_NAME);
            System.exit(1);
        }
    }

    public static void readAndSetProperties(){
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

}
