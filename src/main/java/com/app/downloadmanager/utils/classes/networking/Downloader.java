package com.app.downloadmanager.utils.classes.networking;

import com.app.downloadmanager.model.File;
import com.app.downloadmanager.utils.classes.core.AppProperties;
import com.app.downloadmanager.utils.classes.core.Keys;
import com.app.downloadmanager.utils.interfaces.DownloadManagerNetworkEventListener;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

public class Downloader {

    private BufferedInputStream inputStream;
    private HttpsURLConnection httpsURLConnection;
    private final DownloadManagerNetworkEventListener downloadManagerNetworkEventListener;
    private final File file;
    private RandomAccessFile fileOutputStream;
    private Thread thread;
    private boolean isActive; // Flag to check pause/resume

    public Downloader(File file, DownloadManagerNetworkEventListener downloadManagerNetworkEventListener) throws FileNotFoundException {
        this.file = file;
        isActive = true;
        inputStream = file.getInputStream();
        httpsURLConnection = file.getHttpsURLConnection();
        httpsURLConnection.setReadTimeout(5000);
        httpsURLConnection.setConnectTimeout(8000);
        this.downloadManagerNetworkEventListener = downloadManagerNetworkEventListener;
        file.setDownloadManagerNetworkEventListener(downloadManagerNetworkEventListener);
        file.statusProperty().addListener(((observable, oldValue, newValue) -> {
            switch (newValue.intValue()){
                case Keys.STATUS_PAUSED -> isActive = false;
                case Keys.STATUS_STOPPED -> {
                    isActive = false; // auto pause when stop is clicked
                    destroyConnection();
                }
                case Keys.STATUS_DOWNLOADING -> {
                    java.io.File cachedFile = new java.io.File(file.getSaveLocation()+"/"+file.getFileName()); // reference to the file (might be non-existent)
                    isActive = true;
                    if (fileOutputStream == null){ // Create the file output stream in case the stream doesn't exist(means that the download was stopped earlier)
                        try{
                            fileOutputStream = new RandomAccessFile(file.getSaveLocation()+"/"+file.getFileName(), "rw");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    if (cachedFile.exists()){ // Gets triggered if the file in the download list already exists in the location specified
                        try{
                            httpsURLConnection = (HttpsURLConnection) new URL(file.getUrl()).openConnection();
                            httpsURLConnection.setReadTimeout(5000);
                            httpsURLConnection.setConnectTimeout(8000);
                            httpsURLConnection.setAllowUserInteraction(true);
                            httpsURLConnection.setRequestProperty("Range", "bytes=" + cachedFile.length() + "-"); // Sets the connection request header field to get the content remaining
                            httpsURLConnection.connect();
                            if (httpsURLConnection.getResponseCode() / 100 != 2){
                                throw new ConnectException("Range exception");
                            }
                            String connectionField = httpsURLConnection.getHeaderField("content-range"); // Retrieves the remaining size(if any)
                            if (connectionField != null) {
                                String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                                file.setDownloadedSize(Long.parseLong(connectionRanges[0]));
                                file.setRemaining(file.getTotalSizeLong() - file.getDownloadedSizeLong());
                            }
                            if (connectionField == null && cachedFile.exists())
                                cachedFile.delete();
                            inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
                            fileOutputStream.seek(file.getDownloadedSizeLong());
                        } catch (ConnectException e) { // Gets triggered if the file added has already been downloaded fully
                            file.setStatus(Keys.STATUS_FINISHED);
                            file.setRemaining(0);
                            file.setDownloadedSize(file.getTotalSizeLong());
                            file.setProgress(1.0);
                            return;
                        } catch (IOException e){
                            e.printStackTrace();
//                            downloadManagerNetworkEventListener.onErrorOccurred("Connection to internet lost while downloading "+file.getFileName());
                            return;
                        }
                    }
                    initializeNewDownloadThread();
                    thread.start();
                }
            }
        }));
        file.setStatus(Keys.STATUS_DOWNLOADING);
        if (file.getDownloadedSizeLong() == file.getTotalSizeLong()) { // Temporary code, changes the status if an already downloaded file is added again
            file.setStatus(Keys.STATUS_FINISHED);
//            downloadManagerNetworkEventListener.onDownloadFinished(file.getFileName());
        }
    }

    private void initializeNewDownloadThread(){
        thread = new Thread(() -> {
            try {
                while(inputStream.available() > 0 && isActive) {
                    fileOutputStream.write(inputStream.readNBytes(AppProperties.DEFAULT_PACKET_SIZE));
                    file.setDownloadedSize(file.getDownloadedSizeLong()+AppProperties.DEFAULT_PACKET_SIZE);
                    if(file.isPausable()) {
                        file.setRemaining(file.getRemainingSizeLong() - AppProperties.DEFAULT_PACKET_SIZE);
                        double progress = (double) file.getDownloadedSizeLong() / file.getTotalSizeLong();
                        file.setProgress(progress);
                    }
                    downloadManagerNetworkEventListener.onProgressChanged();
                }
            } catch (Exception e) {
                    e.printStackTrace();
                    if (isActive){
                        downloadManagerNetworkEventListener.onErrorOccurred("Connection to internet lost while downloading "+file.getFileName());
                        file.setStatus(Keys.STATUS_ERROR);
                        destroyConnection();
                    }
            }
        });
    }

    private void destroyConnection(){
        try {
            inputStream.close();
            inputStream = null; // remove the current connection input stream
            fileOutputStream.close();
            fileOutputStream = null; // remove the current file output stream
            httpsURLConnection.disconnect();
            httpsURLConnection = null;  // remove the current url connection
            thread.interrupt(); // final step to close the ongoing download
        }catch (Exception ignored){
        }
    }

}
