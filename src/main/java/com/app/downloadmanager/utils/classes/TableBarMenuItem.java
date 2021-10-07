package com.app.downloadmanager.utils.classes;

import com.app.downloadmanager.controller.DownloadManagerController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class TableBarMenuItem {

    private final Label label;
    private final CheckBox checkBox;
    private final HBox hBox;
    private final BooleanProperty checkBoxEnabledProperty;

    public TableBarMenuItem(String tag, Boolean isEnabled, int tableColumnIndex){
        checkBoxEnabledProperty = new SimpleBooleanProperty();
        label = new Label(tag);
        checkBox = new CheckBox();

        checkBoxEnabledProperty.setValue(isEnabled);
        checkBoxEnabledProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBox.setSelected(newValue);
            }
        });
        checkBox.setSelected(isEnabled);
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (checkBoxEnabledProperty.get()){
                    DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableFinished.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableError.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableStopped.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableDownloading.get(tableColumnIndex).setVisible(false);
                    checkBoxEnabledProperty.set(false);
                }else{
                    DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableFinished.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableError.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableStopped.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableDownloading.get(tableColumnIndex).setVisible(true);
                    checkBoxEnabledProperty.set(true);
                }
            }
        });

        hBox = new HBox();
        hBox.setPrefWidth(150);
        hBox.setPrefHeight(20);
        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(label);
        hBox.setSpacing(15);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (checkBoxEnabledProperty.get()){
                    DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableFinished.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableError.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableStopped.get(tableColumnIndex).setVisible(false);
                    DownloadManagerController.listColumnsTableDownloading.get(tableColumnIndex).setVisible(false);
                    checkBoxEnabledProperty.set(false);
                }else{
                    DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableFinished.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableError.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableStopped.get(tableColumnIndex).setVisible(true);
                    DownloadManagerController.listColumnsTableDownloading.get(tableColumnIndex).setVisible(true);
                    checkBoxEnabledProperty.set(true);
                }
            }
        });
        hBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBox.setStyle("-fx-background-color: rgb(194, 194, 194)");
            }
        });
        hBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBox.setStyle("-fx-background-color: rgb(255, 255, 255)");
            }
        });

        label.setPrefHeight(20);
        label.setPrefWidth(150);
        label.setAlignment(Pos.TOP_LEFT);
    }

    public HBox getHBox() {
        return hBox;
    }
}
