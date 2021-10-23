package com.app.downloadmanager.utils.classes.networking;

import com.app.downloadmanager.utils.classes.core.Keys;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class URLChecker {

    private static HttpsURLConnection httpsURLConnection;

    public static final String KEY_CONTENT_TYPE = "content_type";
    public static final String KEY_FILE_NAME = "file_Name";
    public static final String KEY_CONTENT_LENGTH = "content_length";
    public static final String KEY_PAUSABLE = "is_pausable";
    public static URL url;

    public static int isURLValid(String urlString){
        try {
            url = new URL(urlString);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            if (httpsURLConnection.getResponseCode() == 200)
                return Keys.STATUS_GET_SUCCESSFUL;
            else {
                return Keys.STATUS_CONNECTION_ERROR;
            }
        } catch (MalformedURLException e) {
            return Keys.STATUS_BAD_URL;
        } catch (IOException e){
            return Keys.STATUS_CONNECTION_ERROR;
        }
    }

    public static String getFileName(String urlString){
        return urlString.substring(urlString.lastIndexOf('/')+1);
    }

    public static HashMap<String, Object> getFileInfo(String urlString){
        HashMap<String, Object> fileInfo = new HashMap<>();
        fileInfo.put(KEY_FILE_NAME, getFileName(urlString));
        fileInfo.put(KEY_CONTENT_LENGTH, httpsURLConnection.getContentLengthLong());
        fileInfo.put(KEY_CONTENT_TYPE, getContentType(urlString.substring(urlString.lastIndexOf('.'))));
        if (httpsURLConnection.getContentLengthLong() == -1){
            fileInfo.put(KEY_PAUSABLE, false);
        }else {
            fileInfo.put(KEY_PAUSABLE, true);
        }
        httpsURLConnection = null;
        return fileInfo;
    }

    public static String getContentType(String fileExtension)
    {
        switch (fileExtension)
        {
            case ".mp3", ".aac", ".ogg", ".flac", ".alac", ".wav", ".aiff", ".dsd", ".pcm" -> { return "Audio ("+fileExtension+")"; }
            case ".7z", ".zip", ".bz2", ".tar", ".gzip", ".rar", ".xz", ".tar.gz", ".tar.bz2", ".tar.gzip", ".tar.xz" -> { return "Compressed ("+fileExtension+")";}
            case ".mp4", ".mov", ".wmv", "flv", ".avi", ".avchd", ".webm", ".mkv" -> { return "Video ("+fileExtension+")";}
            case ".jpeg", ".jpg", ".gif", ".tif", ".psd", ".eps", ".ai", ".indd", ".raw" -> { return "Picture ("+fileExtension+")";}
            case ".bat", ".bin", ".cmd", ".com", ".cpl", ".exe", ".gadget", ".inf1", ".ins",".inx", ".isu", ".job", ".jse", ".lnk", ".msc", ".msi", ".msp", ".mst", ".paf", ".pif", ".ps1", ".reg", ".rgs", ".scr", ".sct", ".shb", "shs", ".u3p", ".vb", ".vbe", ".vbs", ".vbscript", ".ws", ".wsf", ".wsh" -> { return "Application ("+fileExtension+")";}
            case ".doc", "docx", "html", "htm", ".odt", ".pdf", ".xls", ".xlsx", ".ods", ".ppt", ".pptx", ".txt" -> { return "Document ("+fileExtension+")";}
            case ".iso" -> { return "Disc Image("+fileExtension+")";}
            default -> { return "Unknown format ("+fileExtension+")"; }
        }
    }

}
