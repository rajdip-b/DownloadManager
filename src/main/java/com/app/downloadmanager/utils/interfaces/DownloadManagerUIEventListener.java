package com.app.downloadmanager.utils.interfaces;

import com.app.downloadmanager.model.File;

public interface DownloadManagerUIEventListener {

    void onDownloadAdded(File file);
    void onCancelClicked();

}
