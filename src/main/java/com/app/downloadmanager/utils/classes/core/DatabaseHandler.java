package com.app.downloadmanager.utils.classes.core;

import com.app.downloadmanager.model.File;
import javafx.application.Platform;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private static Connection connection;

    private static final int INDEX_FILE_NAME = 1;
    private static final int INDEX_TOTAL_SIZE = 2;
    private static final int INDEX_REMAINING_SIZE = 3;
    private static final int INDEX_DOWNLOADED_SIZE = 4;
    private static final int INDEX_URL = 5;
    private static final int INDEX_CREATED_ON = 6;
    private static final int INDEX_FINISHED_ON = 7;
    private static final int INDEX_PROGRESS = 8;
    private static final int INDEX_SAVE_LOCATION = 9;
    private static final int INDEX_IS_PAUSABLE = 10;

    public static boolean checkConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:derby:database;create=false;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void createDatabase(){
        try{
            connection = DriverManager.getConnection("jdbc:derby:database;create=true;");
            Statement statement = connection.createStatement();
            statement.execute("create schema download_manager");
            statement.execute("create table download_manager.downloads (file_name varchar(200) unique, total_size int, remaining_size int, downloaded_size int, url varchar(1000) unique, created_on varchar(100), finished_on varchar(100), progress double, save_location varchar(200), is_pausable varchar(10))");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static int insertDownload(File file) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("insert into download_manager.downloads values(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(INDEX_FILE_NAME, file.getFileName());
            preparedStatement.setDouble(INDEX_TOTAL_SIZE, file.getTotalSizeLong());
            preparedStatement.setDouble(INDEX_REMAINING_SIZE, file.getRemainingSizeLong());
            preparedStatement.setDouble(INDEX_DOWNLOADED_SIZE, file.getDownloadedSizeLong());
            preparedStatement.setString(INDEX_URL, file.getUrl());
            preparedStatement.setString(INDEX_CREATED_ON, file.getCreatedOn());
            preparedStatement.setString(INDEX_FINISHED_ON, file.getFinishedOn());
            preparedStatement.setDouble(INDEX_PROGRESS, file.getProgressDouble());
            preparedStatement.setString(INDEX_SAVE_LOCATION, file.getSaveLocation());
            preparedStatement.setString(INDEX_IS_PAUSABLE, String.valueOf(file.isPausable()));
            preparedStatement.executeUpdate();
            return 0;
        }catch (SQLIntegrityConstraintViolationException e){
            return 1;
        }catch (SQLException a) {
            return 2;
        }
    }

    public static ArrayList<File> getAllDownloads(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from download_manager.downloads");
            ArrayList<File> files = new ArrayList<>();
            while(resultSet.next()){
                File file = new File();
                file.setFileName(resultSet.getString(INDEX_FILE_NAME));
                file.setTotal(resultSet.getLong(INDEX_TOTAL_SIZE));
                file.setRemaining(resultSet.getLong(INDEX_REMAINING_SIZE));
                file.setDownloadedSize(resultSet.getLong(INDEX_DOWNLOADED_SIZE));
                file.setUrl(resultSet.getString(INDEX_URL));
                file.setCreatedOn(resultSet.getString(INDEX_CREATED_ON));
                file.setFinishedOn(resultSet.getString(INDEX_FINISHED_ON));
                file.setProgress(resultSet.getDouble(INDEX_PROGRESS));
                file.setSaveLocation(resultSet.getString(INDEX_SAVE_LOCATION));
                file.setPausable(Boolean.parseBoolean(resultSet.getString(INDEX_IS_PAUSABLE)));
                files.add(file);
            }
            return files;
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static void updateDownloadSizeAndProgress(File file){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update download_manager.downloads set downloaded_size = ?, remaining_size = ?, progress = ? where file_name = ?");
            preparedStatement.setLong(1, file.getDownloadedSizeLong());
            preparedStatement.setLong(2, file.getRemainingSizeLong());
            preparedStatement.setDouble(3, file.getProgressDouble());
            preparedStatement.setString(4, file.getFileName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void updateFinishedDate(File file){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("update download_manager.downloads set finished_on = ? where file_name = ?");
            preparedStatement.setString(1, file.getFinishedOn());
            preparedStatement.setString(2, file.getFileName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void deleteDownload(File file){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("delete from download_manager.downloads where file_name = ?");
            preparedStatement.setString(1, file.getFileName());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

}
