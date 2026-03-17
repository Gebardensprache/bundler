package band.kessoku.gradle.minecraft.manifest.assets;

public record AssetIndex(
        String id,
        String sha1,
        long size,
        String totalSize,
        String url
) {
}
