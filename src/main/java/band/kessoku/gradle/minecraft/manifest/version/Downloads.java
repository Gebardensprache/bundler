package band.kessoku.gradle.minecraft.manifest.version;

import band.kessoku.gradle.api.DownloadEntry;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.Nullable;

public record Downloads(
    Download client,
    @Nullable Download client_mappings,
    Download server,
    @Nullable Download server_mappings
) {

    public record Download(String sha1, long size, String url) implements DownloadEntry {
        @Override public String getUrl(ExtensionAware aware) {return this.url;}
    }

}
