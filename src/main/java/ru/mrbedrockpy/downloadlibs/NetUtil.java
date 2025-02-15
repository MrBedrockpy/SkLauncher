package ru.mrbedrockpy.downloadlibs;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@UtilityClass
public class NetUtil {

    public DownloadStatus downloadFile(String fileURL, String saveDir) {

        try {

            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) return DownloadStatus.SOURCE_NOT_FOUND;

            String fileName;

            String disposition = httpConn.getHeaderField("Content-Disposition");

            if (disposition != null) {
                int startIndex = disposition.indexOf("filename=");
                if (startIndex > 0) {
                    fileName = disposition.substring(startIndex + 9).replace("\"", "");
                }
            }

            fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);

            File directory = new File(saveDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (InputStream inputStream = httpConn.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(saveDir + File.separator + fileName)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (MalformedURLException e) {
            return DownloadStatus.SOURCE_NOT_FOUND;
        } catch (IOException e) {
            return DownloadStatus.DOWNLOAD_ERROR;
        }

        return DownloadStatus.SUCCESS;
    }

    public enum DownloadStatus {
        SUCCESS, SOURCE_NOT_FOUND, DOWNLOAD_ERROR
    }

}
