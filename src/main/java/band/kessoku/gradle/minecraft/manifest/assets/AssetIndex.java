package band.kessoku.gradle.minecraft.manifest.assets;

import band.kessoku.gradle.api.DownloadEntry;
import org.gradle.api.plugins.ExtensionAware;

public record AssetIndex(
        String id,
        String sha1,
        long size,
        String totalSize,
        String url
) implements DownloadEntry {
    @Override
    public String getUrl(ExtensionAware aware) {
        return this.url;
    }
}
