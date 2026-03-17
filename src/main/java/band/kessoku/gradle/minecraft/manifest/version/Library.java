package band.kessoku.gradle.minecraft.manifest.version;

import band.kessoku.gradle.api.DownloadEntry;

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
        public String getUrl() {
            return this.url;
        }

        public File download(File dir) {
            return download(dir.toPath().resolve(this.path));
        }
    }

}
