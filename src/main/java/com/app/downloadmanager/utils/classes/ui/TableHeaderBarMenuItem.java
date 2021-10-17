package com.app.downloadmanager.utils.classes.ui;

import com.app.downloadmanager.controller.DownloadManagerController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TableHeaderBarMenuItem {

    private final CheckBox checkBox;
    private final HBox hBox;
    private final BooleanProperty checkBoxEnabledProperty;

    public TableHeaderBarMenuItem(String tag, Boolean isEnabled, int tableColumnIndex){
        checkBoxEnabledProperty = new SimpleBooleanProperty();
        Label label = new Label(tag);
        checkBox = new CheckBox();

        checkBoxEnabledProperty.setValue(isEnabled);
        checkBoxEnabledProperty.addListener((observable, oldValue, newValue) -> checkBox.setSelected(newValue));
        checkBox.setSelected(isEnabled);
        checkBox.setOnAction(event -> {
            if (checkBoxEnabledProperty.get()){
                DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(false);
                checkBoxEnabledProperty.set(false);
            }else{
                DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(true);
                checkBoxEnabledProperty.set(true);
            }
        });
        hBox = new HBox();
        hBox.setPrefWidth(150);
        hBox.setPrefHeight(20);
        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(label);
        hBox.setSpacing(15);
        hBox.setOnMouseClicked(event -> {
            if (checkBoxEnabledProperty.get()){
                DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(false);
                checkBoxEnabledProperty.set(false);
            }else{
                DownloadManagerController.listColumnsTableAll.get(tableColumnIndex).setVisible(true);
                checkBoxEnabledProperty.set(true);
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
