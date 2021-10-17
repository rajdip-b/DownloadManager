package com.app.downloadmanager.utils.classes.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;

import java.util.Arrays;
import java.util.List;

public class TableHeaderBarMenu {

    private final ContextMenu contextMenu;

    public TableHeaderBarMenu(){
        List<String> labelTags = Arrays.asList("File Name", "Downloaded", "Speed", "Status", "Remaining", "Total", "Save Location", "Created on", "Finished on");
        contextMenu = new ContextMenu();
        for (int x = 0; x < 9; x++) {
            TableHeaderBarMenuItem tableHeaderBarMenuItem = new TableHeaderBarMenuItem(labelTags.get(x), true, x);
            contextMenu.getItems().add(new CustomMenuItem(tableHeaderBarMenuItem.getHBox()));
        }
    }

    public ContextMenu getMenu(){
        return contextMenu;
    }

}
