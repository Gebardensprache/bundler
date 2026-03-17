package band.kessoku.gradle.minecraft.manifest.assets;

import java.util.Map;

public record Assets(Map<String, Asset> objects) {

    public record Asset(
        String hash,
        long size
    ) {}

}
