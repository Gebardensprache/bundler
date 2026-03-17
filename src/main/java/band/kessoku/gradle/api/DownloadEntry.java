package band.kessoku.gradle.api;

import band.kessoku.gradle.utils.HttpUtils;
import okhttp3.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;

public interface DownloadEntry {

    String getUrl();

    default File download(Path path) {
        File file = path.toFile();
        file.mkdirs();

        Request request = new Request.Builder()
                .url(getUrl())
                .build();

        HttpUtils.get(request, response -> {
            try (InputStream inputStream = response.body().byteStream();
                 FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to download file: " + getUrl(), e);
            }
        });
        return file;
    }

}
