package band.kessoku.gradle.api.cache;

import band.kessoku.gradle.api.DownloadEntry;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface Cache {

    Path baseCacheDir();

    default File cacheFile(File file, Path path) {
        File target = baseCacheDir().resolve(path.toString(), file.getName()).toFile();
        if (target.mkdirs() && !target.exists()) {
            try {
                FileUtils.copyFile(file, target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return target;
    }

    default void cacheDownloadEntry(DownloadEntry entry, Path path, ExtensionAware aware) {
        entry.download(baseCacheDir().resolve(path), aware);
    }

}
