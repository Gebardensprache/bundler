package band.kessoku.gradle.utils;

import lombok.experimental.UtilityClass;
import org.gradle.api.plugins.ExtensionAware;

/**
 * Source: <a href="https://github.com/FabricMC/fabric-loom/blob/dev/1.15/src/main/java/net/fabricmc/loom/util/MirrorUtil.java">MirrorUtil</a>
 * @author FabricMC
 */
@UtilityClass
public class MirrorUtil {

    public static String getLibrariesBase(ExtensionAware aware) {
        if (aware.getExtensions().getExtraProperties().has("loom_libraries_base")) {
            return String.valueOf(aware.getExtensions().getExtraProperties().get("loom_libraries_base"));
        }

        return "https://libraries.minecraft.net/";
    }

    public static String getResourcesBase(ExtensionAware aware) {
        if (aware.getExtensions().getExtraProperties().has("loom_resources_base")) {
            return String.valueOf(aware.getExtensions().getExtraProperties().get("loom_resources_base"));
        }

        return "https://resources.download.minecraft.net/";
    }

    public static String getVersionManifests(ExtensionAware aware) {
        if (aware.getExtensions().getExtraProperties().has("loom_version_manifests")) {
            return String.valueOf(aware.getExtensions().getExtraProperties().get("loom_version_manifests"));
        }

        return "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    }

}
