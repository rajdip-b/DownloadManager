package com.app.downloadmanager.controller;

import com.app.downloadmanager.model.File;
import com.app.downloadmanager.utils.classes.AppProperties;
import com.app.downloadmanager.utils.classes.TableBarMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DownloadManagerController {

    @FXML private AnchorPane anchorPane;

    @FXML private TableColumn<File, String> colNameTableAll;
    @FXML private TableColumn<File, String> colDownloadedTableAll;
    @FXML private TableColumn<File, String> colProgressTableAll;
    @FXML private TableColumn<File, String> colStatusTableAll;
    @FXML private TableColumn<File, String> colRemainingTableAll;
    @FXML private TableColumn<File, String> colTotalTableAll;
    @FXML private TableColumn<File, String> colSaveLocationTableAll;
    @FXML private TableColumn<File, String> colCreatedOnTableAll;
    @FXML private TableColumn<File, String> colFinishedOnTableAll;

    @FXML private TableColumn<File, String> colNameTableDownloading;
    @FXML private TableColumn<File, String> colDownloadedTableDownloading;
    @FXML private TableColumn<File, String> colProgressTableDownloading;
    @FXML private TableColumn<File, String> colStatusTableDownloading;
    @FXML private TableColumn<File, String> colRemainingTableDownloading;
    @FXML private TableColumn<File, String> colTotalTableDownloading;
    @FXML private TableColumn<File, String> colSaveLocationTableDownloading;
    @FXML private TableColumn<File, String> colCreatedOnTableDownloading;
    @FXML private TableColumn<File, String> colFinishedOnTableDownloading;

    @FXML private TableColumn<File, String> colNameTableFinished;
    @FXML private TableColumn<File, String> colDownloadedTableFinished;
    @FXML private TableColumn<File, String> colProgressTableFinished;
    @FXML private TableColumn<File, String> colStatusTableFinished;
    @FXML private TableColumn<File, String> colRemainingTableFinished;
    @FXML private TableColumn<File, String> colTotalTableFinished;
    @FXML private TableColumn<File, String> colSaveLocationTableFinished;
    @FXML private TableColumn<File, String> colCreatedOnTableFinished;
    @FXML private TableColumn<File, String> colFinishedOnTableFinished;

    @FXML private TableColumn<File, String> colNameTableStopped;
    @FXML private TableColumn<File, String> colDownloadedTableStopped;
    @FXML private TableColumn<File, String> colProgressTableStopped;
    @FXML private TableColumn<File, String> colStatusTableStopped;
    @FXML private TableColumn<File, String> colRemainingTableStopped;
    @FXML private TableColumn<File, String> colTotalTableStopped;
    @FXML private TableColumn<File, String> colSaveLocationTableStopped;
    @FXML private TableColumn<File, String> colCreatedOnTableStopped;
    @FXML private TableColumn<File, String> colFinishedOnTableStopped;

    @FXML private TableColumn<File, String> colNameTableError;
    @FXML private TableColumn<File, String> colDownloadedTableError;
    @FXML private TableColumn<File, String> colProgressTableError;
    @FXML private TableColumn<File, String> colStatusTableError;
    @FXML private TableColumn<File, String> colRemainingTableError;
    @FXML private TableColumn<File, String> colTotalTableError;
    @FXML private TableColumn<File, String> colSaveLocationTableError;
    @FXML private TableColumn<File, String> colCreatedOnTableError;
    @FXML private TableColumn<File, String> colFinishedOnTableError;

    @FXML private TableView<File> tableAll;
    @FXML private TableView<File> tableDownloading;
    @FXML private TableView<File> tableStopped;
    @FXML private TableView<File> tableError;
    @FXML private TableView<File> tableFinished;

    private ObservableList<File> tableAllFiles;
    private ObservableList<File> tableDownloadingFiles;
    private ObservableList<File> tableStoppedFiles;
    private ObservableList<File> tableErrorFiles;
    private ObservableList<File> tableFinishedFiles;

    public static List<TableColumn<File, String>> listColumnsTableAll;
    public static List<TableColumn<File, String>> listColumnsTableDownloading;
    public static List<TableColumn<File, String>> listColumnsTableStopped;
    public static List<TableColumn<File, String>> listColumnsTableFinished;
    public static List<TableColumn<File, String>> listColumnsTableError;

    private TableBarMenu tableBarMenu;


    @FXML
    public void initialize(){
        initializeTableAll();
        initializeTableDownloading();
        initializeTableError();
        initializeTableStopped();
        initializeTableFinished();
        tableAllFiles.add(new File("Raj", AppProperties.DEFAULT_SAVE_LOCATION, new Date().toString()));
        tableBarMenu = new TableBarMenu();
        tableBarMenu.setInvisible();
        anchorPane.getChildren().add(tableBarMenu.getMenu());
    }

    private void initializeTableAll(){
        tableAllFiles = FXCollections.observableArrayList();
        colNameTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("fileName"));
        colDownloadedTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("downloadedSize"));
        colProgressTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("progress"));
        colStatusTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("status"));
        colRemainingTableAll.setCellValueFactory(new PropertyValueFactory<File, String >("remainingSize"));
        colTotalTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("totalSize"));
        colSaveLocationTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("saveLocation"));
        colCreatedOnTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("createdOn"));
        colFinishedOnTableAll.setCellValueFactory(new PropertyValueFactory<File, String>("finishedOn"));
        tableAll.setItems(tableAllFiles);
        listColumnsTableAll = Arrays.asList(colNameTableAll, colDownloadedTableAll, colProgressTableAll, colStatusTableAll, colRemainingTableAll, colTotalTableAll, colSaveLocationTableAll, colCreatedOnTableAll, colFinishedOnTableAll);
    }

    private void initializeTableDownloading(){
        tableDownloadingFiles = FXCollections.observableArrayList();
        colNameTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("fileName"));
        colDownloadedTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("downloadedSize"));
        colProgressTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("progress"));
        colStatusTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("status"));
        colRemainingTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String >("remainingSize"));
        colTotalTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("totalSize"));
        colSaveLocationTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("saveLocation"));
        colCreatedOnTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("createdOn"));
        colFinishedOnTableDownloading.setCellValueFactory(new PropertyValueFactory<File, String>("finishedOn"));
        tableDownloading.setItems(tableDownloadingFiles);
        listColumnsTableDownloading = Arrays.asList(colNameTableDownloading, colDownloadedTableDownloading, colProgressTableDownloading, colStatusTableDownloading, colRemainingTableDownloading, colTotalTableDownloading, colSaveLocationTableDownloading, colCreatedOnTableDownloading, colFinishedOnTableDownloading);
    }

    private void initializeTableStopped(){
        tableStoppedFiles = FXCollections.observableArrayList();
        colNameTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("fileName"));
        colDownloadedTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("downloadedSize"));
        colProgressTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("progress"));
        colStatusTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("status"));
        colRemainingTableStopped.setCellValueFactory(new PropertyValueFactory<File, String >("remainingSize"));
        colTotalTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("totalSize"));
        colSaveLocationTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("saveLocation"));
        colCreatedOnTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("createdOn"));
        colFinishedOnTableStopped.setCellValueFactory(new PropertyValueFactory<File, String>("finishedOn"));
        tableStopped.setItems(tableStoppedFiles);
        listColumnsTableStopped = Arrays.asList(colNameTableStopped, colDownloadedTableStopped, colProgressTableStopped, colStatusTableStopped, colRemainingTableStopped, colTotalTableStopped, colSaveLocationTableStopped, colCreatedOnTableStopped, colFinishedOnTableStopped);
    }

    private void initializeTableError(){
        tableErrorFiles = FXCollections.observableArrayList();
        colNameTableError.setCellValueFactory(new PropertyValueFactory<File, String>("fileName"));
        colDownloadedTableError.setCellValueFactory(new PropertyValueFactory<File, String>("downloadedSize"));
        colProgressTableError.setCellValueFactory(new PropertyValueFactory<File, String>("progress"));
        colStatusTableError.setCellValueFactory(new PropertyValueFactory<File, String>("status"));
        colRemainingTableError.setCellValueFactory(new PropertyValueFactory<File, String >("remainingSize"));
        colTotalTableError.setCellValueFactory(new PropertyValueFactory<File, String>("totalSize"));
        colSaveLocationTableError.setCellValueFactory(new PropertyValueFactory<File, String>("saveLocation"));
        colCreatedOnTableError.setCellValueFactory(new PropertyValueFactory<File, String>("createdOn"));
        colFinishedOnTableError.setCellValueFactory(new PropertyValueFactory<File, String>("finishedOn"));
        tableError.setItems(tableErrorFiles);
        listColumnsTableError = Arrays.asList(colNameTableError, colDownloadedTableError, colProgressTableError, colStatusTableError, colRemainingTableError, colTotalTableError, colSaveLocationTableError, colCreatedOnTableError, colFinishedOnTableError);
    }

    private void initializeTableFinished(){
        tableFinishedFiles = FXCollections.observableArrayList();
        colNameTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("fileName"));
        colDownloadedTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("downloadedSize"));
        colProgressTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("progress"));
        colStatusTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("status"));
        colRemainingTableFinished.setCellValueFactory(new PropertyValueFactory<File, String >("remainingSize"));
        colTotalTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("totalSize"));
        colSaveLocationTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("saveLocation"));
        colCreatedOnTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("createdOn"));
        colFinishedOnTableFinished.setCellValueFactory(new PropertyValueFactory<File, String>("finishedOn"));
        tableFinished.setItems(tableFinishedFiles);
        listColumnsTableFinished = Arrays.asList(colNameTableFinished, colDownloadedTableFinished, colProgressTableFinished, colStatusTableFinished, colRemainingTableFinished, colTotalTableFinished, colSaveLocationTableFinished, colCreatedOnTableFinished, colFinishedOnTableFinished);
    }

    @FXML
    public void onAddClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void onStopClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void onDeleteClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void onDeleteAllClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void onMousePressed(MouseEvent mouseEvent){
        if (mouseEvent.getButton() == MouseButton.SECONDARY){
            tableBarMenu.setSpawnPosition(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            tableBarMenu.setVisible();
        }else {
            tableBarMenu.setInvisible();
        }
    }
}
