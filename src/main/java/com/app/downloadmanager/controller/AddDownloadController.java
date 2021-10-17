package com.app.downloadmanager.controller;

import com.app.downloadmanager.model.File;
import com.app.downloadmanager.utils.classes.core.AppProperties;
import com.app.downloadmanager.utils.classes.core.Keys;
import com.app.downloadmanager.utils.classes.networking.URLChecker;
import com.app.downloadmanager.utils.interfaces.DownloadManagerUIEventListener;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

public class AddDownloadController {

    @FXML private TextField txtURL;
    @FXML private TextField txtSaveLocation;
    @FXML private TextField txtFileName;

    @FXML private Label lblFileType;
    @FXML private Label lblFileSize;
    @FXML private Label lblLoading;

    @FXML private Button btnAdd;
    @FXML private Button btnBrowse;

    private BooleanProperty urlValidProperty;
    private static DownloadManagerUIEventListener downloadManagerUIEventListener;
    private FileChooser fileChooser;

    private String fileName;
    private String contentType;
    private String saveLocation;
    private Long contentLength;
    private BufferedInputStream inputStream;
    private HttpsURLConnection httpsURLConnection;

    public static void addDownloadManagerUIEventListener(DownloadManagerUIEventListener downloadManagerUIEventListener){
        AddDownloadController.downloadManagerUIEventListener = downloadManagerUIEventListener;
    }

    @FXML
    public void initialize(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new java.io.File(AppProperties.DEFAULT_SAVE_LOCATION));
        fileChooser.setTitle(Keys.STRING_SAVE_FILE);
        lblLoading.setVisible(false);
        txtFileName.setDisable(true);
        txtSaveLocation.setDisable(true);
        btnAdd.setDisable(true);
        btnBrowse.setDisable(true);
        urlValidProperty = new SimpleBooleanProperty();
        urlValidProperty.addListener(((observable, oldValue, newValue) -> {
            if (urlValidProperty.get()){
                btnAdd.setDisable(false);
                btnBrowse.setDisable(false);
                txtFileName.setDisable(false);
                txtSaveLocation.setDisable(false);
            }else {
                btnAdd.setDisable(true);
                btnBrowse.setDisable(true);
                txtFileName.setDisable(true);
                txtSaveLocation.setDisable(true);
            }
        }));
        urlValidProperty.set(false);
        txtURL.textProperty().addListener(((observable, oldValue, newValue) -> new Thread(() -> onTextChangedEvent(newValue)).start()));
        txtSaveLocation.textProperty().addListener(((observable, oldValue, newValue) -> saveLocation = newValue));
        txtFileName.textProperty().addListener(((observable, oldValue, newValue) -> fileName = newValue));
    }

    @FXML
    public void onAddClicked(){
        File file = new File(fileName, saveLocation, new Date().toString(), txtURL.getText(), inputStream, httpsURLConnection);
        file.setTotal(contentLength);
        file.setRemaining(contentLength);
        downloadManagerUIEventListener.onDownloadAdded(file);
    }

    @FXML
    public void onCancelClicked(){
        downloadManagerUIEventListener.onCancelClicked();
    }

    @FXML
    public void onBrowseClicked(){
        fileChooser.setInitialFileName(fileName);
        java.io.File file = fileChooser.showSaveDialog(null);
        if (file != null){
            txtSaveLocation.setText(file.getParent());
            txtFileName.setText(file.getName());
        }
    }

    public void onTextChangedEvent(String url){
        Platform.runLater(() -> lblLoading.setText(Keys.STRING_GATHERING_INFO));
        lblLoading.setVisible(true);
        if (URLChecker.isURLValid(url) == Keys.STATUS_GET_SUCCESSFUL){
            urlValidProperty.set(true);
            HashMap<String, Object> fileInfo = URLChecker.getFileInfo(url);
            assert fileInfo != null;
            fileName = (String) fileInfo.get(URLChecker.KEY_FILE_NAME);
            contentLength = (Long) fileInfo.get(URLChecker.KEY_CONTENT_LENGTH);
            contentType = (String) fileInfo.get(URLChecker.KEY_CONTENT_TYPE);
            inputStream = new BufferedInputStream((InputStream) fileInfo.get(URLChecker.KEY_CONTENT_STREAM));
            httpsURLConnection = (HttpsURLConnection) fileInfo.get(URLChecker.KEY_HTTPS_URL_CONNECTION);
            Platform.runLater(() ->{
                txtSaveLocation.setText(AppProperties.DEFAULT_SAVE_LOCATION);
                txtFileName.setText(fileName);
                lblFileSize.setText(File.convertSizeToHighestMagnitude(contentLength, 0));
                lblFileType.setText(contentType);
            });
            lblLoading.setVisible(false);
        }else{
            Platform.runLater(() -> lblLoading.setText(Keys.STRING_ERROR_NO_INTERNET));
            urlValidProperty.set(false);
            Platform.runLater(() ->{
                txtSaveLocation.setText("");
                txtFileName.setText("");
                lblFileSize.setText("");
                lblFileType.setText("");
            });
        }
    }

}
