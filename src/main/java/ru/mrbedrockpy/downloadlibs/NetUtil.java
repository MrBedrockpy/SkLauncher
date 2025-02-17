package ru.mrbedrockpy.downloadlibs;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@UtilityClass
public class NetUtil {

    public NetStatus downloadFile(String fileURL, String saveDir) {

        try {

            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) return NetStatus.HTTP_ERROR;

            String fileName = getFileName(httpConn, fileURL);

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
            return NetStatus.HTTP_ERROR;
        } catch (IOException e) {
            return NetStatus.DOWNLOAD_ERROR;
        }

        return NetStatus.SUCCESS;
    }

    public JSONObject getJSONObject(String fileURL) {

        URL url;

        try {
            url = new URL(fileURL);
        } catch (MalformedURLException e) {
            return new JSONObject();
        }

        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return new JSONObject(response.toString());
            } else {
                return new JSONObject();
            }

        } catch (IOException e) {
            return new JSONObject();
        }

    }

    private static String getFileName(HttpURLConnection httpConn, String fileURL) {
        String disposition = httpConn.getHeaderField("Content-Disposition");
        if (disposition != null) {
            int startIndex = disposition.indexOf("filename=");
            if (startIndex > 0) {
                return disposition.substring(startIndex + 9).replace("\"", "");
            }
        }
        return fileURL.substring(fileURL.lastIndexOf("/") + 1);
    }

    public enum NetStatus {
        SUCCESS, HTTP_ERROR, DOWNLOAD_ERROR
    }

}
