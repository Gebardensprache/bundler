package band.kessoku.gradle.minecraft.cache.assets;

import band.kessoku.gradle.api.cache.Cache;
import band.kessoku.gradle.minecraft.manifest.assets.AssetIndex;
import band.kessoku.gradle.minecraft.manifest.assets.Assets;
import org.gradle.api.plugins.ExtensionAware;

import java.nio.file.Path;

public class AssetsCache implements Cache {

    private final Path baseCacheDir;

    private AssetsCache(Path cacheRoot) {
        this.baseCacheDir = cacheRoot.resolve("assets");
    }

    @Override
    public Path baseCacheDir() {
        return baseCacheDir;
    }

    public Path getAssetIndexCacheDir() {
        return baseCacheDir().resolve("indexes");
    }

    public Path getAssetFilesCacheDir() {
        return baseCacheDir().resolve("objects");
    }

    public void cacheAssets(AssetIndex index, String gameVersion, ExtensionAware aware) {
        if (getIndex(gameVersion).toFile().exists()) return;

        // Download index file
        this.cacheDownloadEntry(index, getAssetIndexCacheDir().resolve("%s.json".formatted(gameVersion)), aware);

        Assets assets = Assets.fromUrl(index.url());
        assets.objects().forEach((_, asset) -> {
            String hash = asset.hash();
            String subDir = hash.substring(0, 2);
            this.cacheDownloadEntry(asset, getAssetFilesCacheDir().resolve("%s/%s".formatted(subDir, hash)), aware);
        });
    }

    public Path getIndex(String gameVersion) {
        return getAssetIndexCacheDir().resolve("%s.json".formatted(gameVersion));
    }
}
