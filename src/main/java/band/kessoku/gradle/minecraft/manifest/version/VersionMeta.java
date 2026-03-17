package band.kessoku.gradle.minecraft.manifest.version;

import band.kessoku.gradle.minecraft.manifest.assets.AssetIndex;

import java.util.List;

public record VersionMeta(
        Arguments arguments,
        AssetIndex assetIndex,
        String assets,
        int complianceLevel,
        Downloads downloads,
        String id,
        JavaVersion javaVersion,
        List<Library> libraries,
        Logging logging,
        String mainClass,
        int minimumLauncherVersion,
        String releaseTime,
        String time,
        String type
) {

}
