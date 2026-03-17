package band.kessoku.gradle.minecraft.manifest;

public record VersionManifest(
        Latest latest,
        Version[] versions
) {

    public record Latest(
            String release,
            String snapshot
    ) {}

    public record Version(
            String id,
            String type,
            String url,
            String time,
            String releaseTime,
            String sha1,
            int complianceLevel
    ) {}

}
