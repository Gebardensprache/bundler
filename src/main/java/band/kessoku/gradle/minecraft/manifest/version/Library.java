package band.kessoku.gradle.minecraft.manifest.version;

import band.kessoku.gradle.api.DownloadEntry;
import band.kessoku.gradle.utils.MirrorUtil;
import org.gradle.api.plugins.ExtensionAware;

import java.io.File;
import java.util.List;

public record Library(
        Download downloads,
        String name,
        List<Arguments.Rule> rules
) {

    public record Download(
        Artifact artifact
    ) {}

    public record Artifact(
            String path,
            String sha1,
            long size,
            String url
    ) implements DownloadEntry {
        @Override
        public String getUrl(ExtensionAware aware) {
            return this.url.replace("https://libraries.minecraft.net/", MirrorUtil.getLibrariesBase(aware));
        }

        public File download(File dir, ExtensionAware aware) {
            return download(dir.toPath().resolve(this.path), aware);
        }
    }

}
