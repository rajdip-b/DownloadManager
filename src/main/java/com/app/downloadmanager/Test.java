package com.app.downloadmanager;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        try{
            URL url = new URL("https://download-installer.cdn.mozilla.net/pub/firefox/releases/93.0/linux-x86_64/en-US/firefox-93.0.tar.bz2");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            File outputFileCache = new File("/home/rajdip/file.tar.bz2");
            if (outputFileCache.exists())
            {
                connection.setAllowUserInteraction(true);
                connection.setRequestProperty("Range", "bytes=" + outputFileCache.length() + "-");
            }

            connection.setConnectTimeout(14000);
            connection.setReadTimeout(20000);
            connection.connect();

            if (connection.getResponseCode() / 100 != 2) {
//                throw new Exception("Invalid response code!");
                System.out.println(connection.getResponseCode());
            }
            else
            {
                String connectionField = connection.getHeaderField("content-range");
                Long downloadedSize = 0L;
                if (connectionField != null)
                {
                    String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                    downloadedSize = Long.valueOf(connectionRanges[0]);
                }

                if (connectionField == null && outputFileCache.exists())
                    outputFileCache.delete();

                long fileLength = connection.getContentLength() + downloadedSize;
                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                RandomAccessFile output = new RandomAccessFile(outputFileCache, "rw");
                output.seek(downloadedSize);

                byte data[] = new byte[1024];
                int count = 0;
                double __progress = 0.0;

                while ((count = input.read(data, 0, 1024)) != -1
                        && __progress != 100)
                {
                    downloadedSize += count;
                    output.write(data, 0, count);
                    __progress = (double) ((downloadedSize * 100) / fileLength);
                    System.out.println(__progress);
                }
                output.close();
                input.close();
            }
        } catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();
        }
    }

}
