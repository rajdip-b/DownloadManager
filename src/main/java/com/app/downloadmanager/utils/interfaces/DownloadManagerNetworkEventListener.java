package com.app.downloadmanager.utils.interfaces;

public interface DownloadManagerNetworkEventListener {

    void onProgressChanged();
    void onDownloadFinished(String filename);
    void onErrorOccurred(String errorMessage);

}
