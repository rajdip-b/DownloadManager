package com.app.downloadmanager.model;

public class File {

    private String fileName;
    private long fileSize;
    private long url;

    public File(String fileName, long fileSize, long url) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getUrl() {
        return url;
    }

    public void setUrl(long url) {
        this.url = url;
    }
}
