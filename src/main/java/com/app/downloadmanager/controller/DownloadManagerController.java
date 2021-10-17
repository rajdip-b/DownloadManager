package com.app.downloadmanager.controller;

import com.app.downloadmanager.model.File;
import com.app.downloadmanager.utils.classes.core.DatabaseHandler;
import com.app.downloadmanager.utils.classes.core.Keys;
import com.app.downloadmanager.utils.classes.networking.Downloader;
import com.app.downloadmanager.utils.classes.ui.TableHeaderBarMenu;
import com.app.downloadmanager.utils.classes.ui.UserInterface;
import com.app.downloadmanager.utils.interfaces.DownloadManagerNetworkEventListener;
import com.app.downloadmanager.utils.interfaces.DownloadManagerUIEventListener;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DownloadManagerController implements DownloadManagerUIEventListener, DownloadManagerNetworkEventListener {

    @FXML private TableColumn<File, String> colNameTableAll;
    @FXML private TableColumn<File, String> colDownloadedTableAll;
    @FXML private TableColumn<File, ProgressBar> colProgressTableAll;
    @FXML private TableColumn<File, String> colSpeedTableAll;
    @FXML private TableColumn<File, String> colStatusTableAll;
    @FXML private TableColumn<File, String> colRemainingTableAll;
    @FXML private TableColumn<File, String> colTotalTableAll;
    @FXML private TableColumn<File, String> colSaveLocationTableAll;
    @FXML private TableColumn<File, String> colCreatedOnTableAll;
    @FXML private TableColumn<File, String> colFinishedOnTableAll;

    @FXML private TableView<File> tableAll;

    @FXML private Button btnPauseResume;
    @FXML private Button btnStartStop;
    @FXML private Button btnDelete;
    @FXML private Button btnDeleteAll;

    @FXML private HBox buttonBox;

    private ObservableList<File> tableAllFiles;

    public static List<TableColumn<File, String>> listColumnsTableAll;

    private Stage currentStage;

    @FXML
    public void initialize(){
        initializeTableAll();
        TableHeaderBarMenu tableHeaderBarMenu = new TableHeaderBarMenu();
        for (File file : Objects.requireNonNull(DatabaseHandler.getAllDownloads()))
            createNewDownload(file);
        tableAll.setContextMenu(tableHeaderBarMenu.getMenu());
        btnDelete.setDisable(true);
        btnDeleteAll.setDisable(true);
        btnStartStop.setDisable(true);
        btnPauseResume.setDisable(true);
    }

    private void initializeTableAll(){
        tableAllFiles = FXCollections.observableArrayList();
        colNameTableAll.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        colDownloadedTableAll.setCellValueFactory(new PropertyValueFactory<>("downloadedSize"));
        colProgressTableAll.setCellValueFactory(new PropertyValueFactory<>("progress"));
        colSpeedTableAll.setCellValueFactory(new PropertyValueFactory<>("speed"));
        colStatusTableAll.setCellValueFactory(new PropertyValueFactory<>("statusStr"));
        colRemainingTableAll.setCellValueFactory(new PropertyValueFactory<>("remainingSize"));
        colTotalTableAll.setCellValueFactory(new PropertyValueFactory<>("totalSize"));
        colSaveLocationTableAll.setCellValueFactory(new PropertyValueFactory<>("saveLocation"));
        colCreatedOnTableAll.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
        colFinishedOnTableAll.setCellValueFactory(new PropertyValueFactory<>("finishedOn"));
        colNameTableAll.setMinWidth(300);
        colProgressTableAll.setMinWidth(200);
        colStatusTableAll.setMinWidth(130);
        colFinishedOnTableAll.setMinWidth(200);
        colCreatedOnTableAll.setMinWidth(200);
        tableAll.setItems(tableAllFiles);
        listColumnsTableAll = Arrays.asList(colNameTableAll, colDownloadedTableAll, colSpeedTableAll, colStatusTableAll, colRemainingTableAll, colTotalTableAll, colSaveLocationTableAll, colCreatedOnTableAll, colFinishedOnTableAll);
    }

    @FXML
    public void onAddClicked() {
        buttonBox.setDisable(true);
        currentStage = UserInterface.getAddDownloadStage();
        currentStage.setOnCloseRequest((event -> buttonBox.setDisable(false)));
        currentStage.show();
        AddDownloadController.addDownloadManagerUIEventListener(this);
    }

    @FXML
    public void onDeleteClicked() {
        File selectedFile = tableAll.getSelectionModel().getSelectedItem();
        new Alert(Alert.AlertType.CONFIRMATION, "Delete "+selectedFile.getFileName()+"?", ButtonType.YES, ButtonType.CANCEL).showAndWait().ifPresent((buttonType1 -> {
            if (buttonType1 == ButtonType.YES){
                new Alert(Alert.AlertType.CONFIRMATION, "Also delete local files?", ButtonType.YES, ButtonType.NO).showAndWait().ifPresent((buttonType2 -> {
                    if (buttonType2 == ButtonType.YES){
                        try{
                            new java.io.File(selectedFile.getSaveLocation()+"/"+selectedFile.getFileName()).delete();
                        }catch (Exception ignored){}
                        tableAllFiles.remove(selectedFile);
                    }
                }));
                DatabaseHandler.deleteDownload(selectedFile);
            }
        }));
        tableAllFiles.remove(selectedFile);
    }

    @FXML
    public void onStartStopClicked(){
        try{
            File selectedFile = tableAll.getSelectionModel().getSelectedItem();
            if (selectedFile.getStatusInteger() == Keys.STATUS_DOWNLOADING || selectedFile.getStatusInteger() == Keys.STATUS_PAUSED){
                selectedFile.setStatus(Keys.STATUS_STOPPED);
            }else{
                selectedFile.setStatus(Keys.STATUS_DOWNLOADING);
            }
            tableAll.refresh();
        }catch (NullPointerException ignored){}
    }

    @FXML
    public void onPauseResumeClicked(){
        try{
            File selectedFile = tableAll.getSelectionModel().getSelectedItem();
            if (selectedFile.getStatusInteger() == Keys.STATUS_DOWNLOADING){
                selectedFile.setStatus(Keys.STATUS_PAUSED);
            }else{
                selectedFile.setStatus(Keys.STATUS_DOWNLOADING);
            }
            tableAll.refresh();
        }catch (NullPointerException ignored){}
    }

    @FXML
    public void onDeleteAllClicked() {
    }

    @FXML
    public void onTableClicked(){
        File selectedFile;
        try{
            selectedFile = tableAll.getSelectionModel().getSelectedItem();
            switch (selectedFile.getStatusInteger()){
                case Keys.STATUS_DOWNLOADING -> {
                    btnPauseResume.setText("Pause");
                    btnStartStop.setText("Stop");
                    btnPauseResume.setDisable(false);
                    btnStartStop.setDisable(false);
                    btnDeleteAll.setDisable(false);
                    btnDelete.setDisable(false);
                }
                case Keys.STATUS_FINISHED -> {
                    btnPauseResume.setText("Pause");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setText("Start");
                    btnStartStop.setDisable(true);
                    btnDelete.setDisable(false);
                    btnDeleteAll.setDisable(false);
                }
                case Keys.STATUS_ERROR -> {
                    btnPauseResume.setText("Pause");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setText("Start");
                    btnStartStop.setDisable(false);
                    btnDelete.setDisable(false);
                    btnDeleteAll.setDisable(false);
                }
                case Keys.STATUS_STOPPED -> {
                    btnPauseResume.setText("Resume");
                    btnPauseResume.setDisable(false);
                    btnStartStop.setText("Start");
                    btnStartStop.setDisable(true);
                    btnDelete.setDisable(false);
                    btnPauseResume.setDisable(true);
                    btnStartStop.setDisable(false);
                    btnDeleteAll.setDisable(false);
                }
                case Keys.STATUS_PAUSED -> {
                    btnPauseResume.setText("Stop");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setText("Start");
                    btnStartStop.setDisable(false);
                    btnDelete.setDisable(false);
                    btnPauseResume.setDisable(false);
                    btnStartStop.setDisable(false);
                    btnDeleteAll.setDisable(false);
                }
            }
        }
        catch (NullPointerException ignored){
        }
    }

    @Override
    public void onDownloadAdded(File file) {
        buttonBox.setDisable(false);
        if (DatabaseHandler.insertDownload(file) == 1){
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Another download with the same link already exists!").show());
            return;
        }
        currentStage.close();
        createNewDownload(file);
    }

    private void createNewDownload(File file){
        tableAllFiles.add(file);
        file.statusProperty().addListener(((observable, oldValue, newValue) -> {
            switch (newValue.intValue()){
                case Keys.STATUS_DOWNLOADING -> Platform.runLater(() -> {
                    btnPauseResume.setText("Pause");
                    btnStartStop.setText("Stop");
                    btnPauseResume.setDisable(false);
                    btnStartStop.setDisable(false);
                });
                case Keys.STATUS_PAUSED -> Platform.runLater(() -> {
                    btnPauseResume.setText("Resume");
                    btnStartStop.setText("Stop");
                    btnPauseResume.setDisable(false);
                    btnStartStop.setDisable(false);
                });
                case Keys.STATUS_STOPPED -> Platform.runLater(() -> {
                    btnPauseResume.setText("Pause");
                    btnStartStop.setText("Start");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setDisable(false);
                });
                case Keys.STATUS_NULL, Keys.STATUS_FINISHED -> Platform.runLater(() -> {
                    btnPauseResume.setText("Pause");
                    btnStartStop.setText("Stop");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setDisable(true);
                });
                case Keys.STATUS_ERROR -> Platform.runLater(() -> {
                    btnPauseResume.setText("Pause");
                    btnStartStop.setText("Stop");
                    btnPauseResume.setDisable(true);
                    btnStartStop.setDisable(false);
                });
            }
        }));
        try{
            new Downloader(file, this);
        }catch (IOException e){
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Can't create the file over here!"));
        }
    }

    @Override
    public void onCancelClicked() {
        currentStage.close();
        buttonBox.setDisable(false);
    }

    @Override
    public synchronized void onProgressChanged(File file) {
        tableAll.refresh();
        DatabaseHandler.updateDownloadSizeAndProgress(file);
    }

    @Override
    public synchronized void onDownloadFinished(File file) {
        tableAll.refresh();
        DatabaseHandler.updateFinishedDate(file);
        Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION, file.getFileName() + " has finished downloading!").show());
    }

    @Override
    public synchronized void onErrorOccurred(String errorMessage) {
        tableAll.refresh();
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, errorMessage).show());
    }
}
