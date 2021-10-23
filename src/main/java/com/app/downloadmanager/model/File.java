package com.app.downloadmanager.model;

import com.app.downloadmanager.utils.classes.core.AppProperties;
import com.app.downloadmanager.utils.classes.core.DatabaseHandler;
import com.app.downloadmanager.utils.classes.core.Keys;
import com.app.downloadmanager.utils.interfaces.DownloadManagerNetworkEventListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ProgressBar;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.util.Date;

public class File {

    private String fileName;
    private double speed;
    private double tempDownloadedSize;
    private long startTime;
    private long downloadedSize;
    private double progress;
    private final SimpleIntegerProperty status;
    private long remainingSize;
    private long totalSize;
    private String url;
    private String saveLocation;
    private String createdOn;
    private String finishedOn;
    private boolean isPausable;
    private BufferedInputStream inputStream;
    private HttpsURLConnection httpsURLConnection;
    private static final String[] prefixes = {"B", "KB", "MB", "GB"};
    private static final String[] statuses = {"Unknown", "Stopped", "Downloading", "Error", "Finished", "Paused"};
    private final ProgressBar progressBar;
    private String statusStr;
    private DownloadManagerNetworkEventListener downloadManagerNetworkEventListener;

    public File(){
        status = new SimpleIntegerProperty();
        progressBar = new ProgressBar();
        progressBar.setMinWidth(190);
        progressBar.setMinHeight(25);
        fileName = "";
        downloadedSize = 0L;
        progress = 0.0d;
        status.set(Keys.STATUS_NULL);
        remainingSize = 0L;
        totalSize = 0L;
        saveLocation = "";
        createdOn = "";
        finishedOn = "";
        url = "";
        inputStream = null;
        httpsURLConnection = null;
        status.addListener(((observable, oldValue, newValue) -> {
            startTime = System.currentTimeMillis();
            tempDownloadedSize = 0;
            speed = 0;
            try{
                switch (newValue.intValue()){
                    case Keys.STATUS_DOWNLOADING -> progressBar.setStyle("-fx-accent: rgb(39, 195, 230)");
                    case Keys.STATUS_PAUSED, Keys.STATUS_STOPPED -> progressBar.setStyle("-fx-accent: rgb(122, 122, 122)");
                    case Keys.STATUS_ERROR -> progressBar.setStyle("-fx-accent: rgb(235, 54, 54)");
                    case Keys.STATUS_FINISHED -> {
                        progressBar.setStyle("-fx-accent: rgb(54, 235, 99)");
                        downloadManagerNetworkEventListener.onDownloadFinished(this);
                        speed = 0;
                        setRemaining(0);
                        setFinishedOn(new Date().toString());
                    }
                }
            }catch (NullPointerException ignored){}
        }));
    }

    public File(String fileName, String saveLocation, String createdOn, String url){
        this();
        this.fileName = fileName;
        this.saveLocation = saveLocation;
        this.createdOn = createdOn;
        this.url = url;
        remainingSize = totalSize;
    }

    public File(String fileName, long downloadedSize, double progress, int status, long totalSize, String saveLocation, String createdOn, String finishedOn, String url, BufferedInputStream inputStream, HttpsURLConnection httpsURLConnection) {
        this();
        this.fileName = fileName;
        this.downloadedSize = downloadedSize;
        this.progress = progress;
        this.status.set(status);
        remainingSize = totalSize;
        this.totalSize = totalSize;
        this.saveLocation = saveLocation;
        this.createdOn = createdOn;
        this.finishedOn = finishedOn;
        this.url = url;
        this.inputStream = inputStream;
        this.httpsURLConnection = httpsURLConnection;
    }

    public void setDownloadManagerNetworkEventListener(DownloadManagerNetworkEventListener downloadManagerNetworkEventListener){
        this.downloadManagerNetworkEventListener = downloadManagerNetworkEventListener;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPausable(boolean isPausable){
        this.isPausable = isPausable;
    }

    public boolean isPausable(){
        return isPausable;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
        tempDownloadedSize = tempDownloadedSize+ AppProperties.DEFAULT_PACKET_SIZE;
        speed = tempDownloadedSize / (System.currentTimeMillis() - startTime);
        if (this.downloadedSize >= totalSize) {
            setStatus(Keys.STATUS_FINISHED);
        }
    }

    public String getSpeed(){
        if (speed/1000 > 0)
            return String.format("%4.2f MB/s", speed/1000);
        else
            return String.format("%4.2f KB/s", speed/100);
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setStatus(int status) {
        this.status.set(status);
        statusStr = statuses[status];
        DatabaseHandler.updateStatus(this);
    }

    public void setRemaining(long remainingSize) {
        this.remainingSize = remainingSize;
    }

    public void setTotal(long totalSize) {
        this.totalSize = totalSize;
        setPausable(totalSize != -1);
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

    public ProgressBar getProgress() {
        progressBar.setProgress(progress);
        return progressBar;
    }

    public double getProgressDouble(){
        return progress;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public int getStatusInteger(){
        return status.get();
    }

    public SimpleIntegerProperty statusProperty(){
        return status;
    }

    public String getRemainingSize() {
        if (isPausable)
            return convertSizeToHighestMagnitude(remainingSize, 0);
        else
            return "Unknown";
    }

    public String getTotalSize() {
        if (isPausable)
            return convertSizeToHighestMagnitude(totalSize, 0);
        else
            return "Unknown";
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpsURLConnection getHttpsURLConnection() {
        return httpsURLConnection;
    }

    public void setHttpsURLConnection(HttpsURLConnection httpsURLConnection) {
        this.httpsURLConnection = httpsURLConnection;
    }

    public long getDownloadedSizeLong(){
        return downloadedSize;
    }

    public long getTotalSizeLong(){
        return totalSize;
    }

    public long getRemainingSizeLong(){
        return remainingSize;
    }

    public static String convertSizeToHighestMagnitude(double bytes, int level){
        if (bytes/1024 >= 1)
            return convertSizeToHighestMagnitude(bytes / 1024, level + 1);
        else
            return String.format("%4.2f %s", bytes, prefixes[level]);
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", speed=" + speed +
                ", downloadedSize=" + downloadedSize +
                ", progress=" + progress +
                ", status=" + statusStr +
                ", remainingSize=" + remainingSize +
                ", totalSize=" + totalSize +
                ", url='" + url + '\'' +
                ", saveLocation='" + saveLocation + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", finishedOn='" + finishedOn + '\'' +
                ", isPausable=" + isPausable +
                '}';
    }
}
