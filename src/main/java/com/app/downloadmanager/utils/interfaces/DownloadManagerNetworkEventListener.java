package com.app.downloadmanager.utils.interfaces;

import com.app.downloadmanager.model.File;

public interface DownloadManagerNetworkEventListener {

    void onProgressChanged(File file);
    void onDownloadFinished(File file);
    void onErrorOccurred(String errorMessage);

}
