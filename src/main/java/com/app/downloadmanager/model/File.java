package com.app.downloadmanager.model;

import com.app.downloadmanager.utils.classes.Keys;

import java.security.Key;

public class File {

    private String fileName;
    private long downloadedSize;
    private double progress;
    private int status;
    private long remainingSize;
    private long totalSize;
    private String saveLocation;
    private String createdOn;
    private String finishedOn;
    private String[] prefixes = {"KB", "MB", "GB"};
    private String[] statuses = {"Unknown", "Stopped", "Downloading", "Error", "Finished"};

    public File(){
        fileName = "";
        downloadedSize = 0L;
        progress = 0.0d;
        status = Keys.STATUS_NULL;
        remainingSize = 0L;
        totalSize = 0L;
        saveLocation = "";
        createdOn = "";
        finishedOn = "";
    }

    public File(String fileName, String saveLocation, String createdOn){
        this();
        this.fileName = fileName;
        this.saveLocation = saveLocation;
        this.createdOn = createdOn;
    }

    public File(String fileName, String saveLocation, String createdOn, int status){
        this();
        this.fileName = fileName;
        this.saveLocation = saveLocation;
        this.createdOn = createdOn;
        this.status = status;
    }

    public File(String fileName, long downloadedSize, double progress, int status, long remainingSize, long totalSize, String saveLocation, String createdOn, String finishedOn) {
        this.fileName = fileName;
        this.downloadedSize = downloadedSize;
        this.progress = progress;
        this.status = status;
        this.remainingSize = remainingSize;
        this.totalSize = totalSize;
        this.saveLocation = saveLocation;
        this.createdOn = createdOn;
        this.finishedOn = finishedOn;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize/1024;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemaining(long remainingSize) {
        this.remainingSize = remainingSize/1024;
    }

    public void setTotal(long totalSize) {
        this.totalSize = totalSize/1024;
    }

    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setFinishedOn(String finishedOn) {
        this.finishedOn = finishedOn;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDownloadedSize() {
        return convertSizeToHighestMagnitude(downloadedSize, 0);
    }

    public String getProgress() {
        return String.format("%3.2f", progress*100);
    }

    public String getStatus() {
        return statuses[status];
    }

    public String getRemainingSize() {
        return convertSizeToHighestMagnitude(remainingSize, 0);
    }

    public String getTotalSize() {
        return convertSizeToHighestMagnitude(totalSize, 0);
    }

    public String getSaveLocation() {
        return saveLocation;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getFinishedOn() {
        return finishedOn;
    }

    public String convertSizeToHighestMagnitude(double bytes, int level){
        if (bytes/1024 >= 1)
            return convertSizeToHighestMagnitude(bytes / 1024, level + 1);
        else
            return String.format("%4.2f %s", bytes, prefixes[level]);
    }

}
