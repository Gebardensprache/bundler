package band.kessoku.gradle.minecraft.cache.minecraft;

import band.kessoku.gradle.api.cache.Cache;
import band.kessoku.gradle.minecraft.manifest.version.Downloads;
import net.neoforged.mergetool.AnnotationVersion;
import net.neoforged.mergetool.Merger;
import org.gradle.api.plugins.ExtensionAware;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public record MinecraftCache(Path baseCacheDir) implements Cache {

    public MinecraftCache(Path baseCacheDir) {
        this.baseCacheDir = baseCacheDir.resolve("minecraft-jars");
    }

    public void cacheVersion(Downloads downloads, String gameVersion, ExtensionAware aware) {
        File versionDir = baseCacheDir().resolve(gameVersion).toFile();
        if (versionDir.exists()) return;
        if (versionDir.mkdirs()) {
            Path path = Path.of(gameVersion);
            cacheDownloadEntry(downloads.client(), path.resolve("client.jar"), aware);
            cacheDownloadEntry(downloads.server(), path.resolve("server.jar"), aware);
            if (downloads.server_mappings() != null && downloads.client_mappings() != null) {
                cacheDownloadEntry(downloads.server_mappings(), path.resolve("server-mappings.txt"), aware);
                cacheDownloadEntry(downloads.client_mappings(), path.resolve("client-mappings.txt"), aware);
            }

            File clientJar = baseCacheDir().resolve(gameVersion).resolve("client.jar").toFile();
            File serverJar = baseCacheDir().resolve(gameVersion).resolve("server.jar").toFile();
            if (clientJar.isFile() && serverJar.isFile()) {
                try {
                    new Merger(clientJar, serverJar, baseCacheDir().resolve(gameVersion).resolve("merged_neoforge.jar").toFile())
                            .annotate(AnnotationVersion.API, true)
                            .keepData()
                            .skipMeta()
                            .process();

                    new Merger(clientJar, serverJar, baseCacheDir().resolve(gameVersion).resolve("merged_fabric.jar").toFile())
                            .annotate(AnnotationVersion.FABRIC, true)
                            .keepData()
                            .skipMeta()
                            .process();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public File getMergedJar(String gameVersion, String loader) {
        return baseCacheDir().resolve(gameVersion).resolve("merged_" + loader + ".jar").toFile();
    }

    public File getClientJar(String gameVersion) {
        return baseCacheDir().resolve(gameVersion).resolve("client.jar").toFile();
    }

    public File getServerJar(String gameVersion) {
        return baseCacheDir().resolve(gameVersion).resolve("server.jar").toFile();
    }
}
