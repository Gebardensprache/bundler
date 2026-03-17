package band.kessoku.gradle.minecraft.manifest.assets;

import band.kessoku.gradle.KessokuGradlePlugin;
import band.kessoku.gradle.api.DownloadEntry;
import band.kessoku.gradle.utils.HttpUtil;
import band.kessoku.gradle.utils.MirrorUtil;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.gradle.api.plugins.ExtensionAware;

import java.io.IOException;
import java.util.Map;

public record Assets(Map<String, Asset> objects) {

    public static Assets fromUrl(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (ResponseBody responseBody = HttpUtil.get(request).body()) {
            return KessokuGradlePlugin.GSON.fromJson(responseBody.string(), Assets.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public record Asset(
        String hash,
        long size
    ) implements DownloadEntry {
        @Override
        public String getUrl(ExtensionAware aware) {
            return MirrorUtil.getResourcesBase(aware) + "/" + hash.substring(0, 2) + "/" + hash;
        }
    }

}
