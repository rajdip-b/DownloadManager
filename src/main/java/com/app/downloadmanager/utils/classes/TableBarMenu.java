package com.app.downloadmanager.utils.classes;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableBarMenu {

    private final int INDEX_FILE_NAME = 0;
    private final int INDEX_DOWNLOADED_SIZE = 1;
    private final int INDEX_PROGRESS = 2;
    private final int INDEX_STATUS = 3;
    private final int INDEX_REMAINING_SIZE = 4;
    private final int INDEX_TOTAL_SIZE = 5;
    private final int INDEX_SAVE_LOCATION = 6;
    private final int INDEX_CREATED_ON = 7;
    private final int INDEX_FINISHED_ON = 8;

    private VBox vbox;
    private ArrayList<TableBarMenuItem> menuItems;
    private final List<String> labelTags;

    public TableBarMenu(){
        menuItems = new ArrayList<>();
        labelTags = Arrays.asList("File Name", "Downloaded", "Progress", "Status", "Remaining", "Total", "Save Location", "Created on", "Finished on");
        DropShadow dropShadow = new DropShadow(10, Color.BLACK);
        vbox = new VBox();
        vbox.setStyle("-fx-background-color: rgb(255,255,255)");
        vbox.setPrefHeight(180);
        vbox.setPrefWidth(150);
        vbox.setEffect(dropShadow);
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        for (int x = 0; x < 9; x++) {
            TableBarMenuItem tableBarMenuItem = new TableBarMenuItem(labelTags.get(x), true, x);
            vbox.getChildren().add(tableBarMenuItem.getHBox());
            menuItems.add(tableBarMenuItem);
        }
    }

    public void setSpawnPosition(double x, double y){
        vbox.setTranslateX(x);
        vbox.setTranslateY(y);
    }

    public void setVisible(){
        vbox.setVisible(true);
    }

    public void setInvisible(){
        vbox.setVisible(false);
    }

    public VBox getMenu(){
        return vbox;
    }

}
