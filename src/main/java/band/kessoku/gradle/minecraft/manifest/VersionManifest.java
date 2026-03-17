package band.kessoku.gradle.minecraft.manifest;

import band.kessoku.gradle.KessokuGradlePlugin;
import band.kessoku.gradle.utils.HttpUtil;
import band.kessoku.gradle.utils.MirrorUtil;
import okhttp3.Request;
import okhttp3.Response;
import org.gradle.api.plugins.ExtensionAware;

import java.io.IOException;

public record VersionManifest(
        Latest latest,
        Version[] versions
) {

    public static VersionManifest get(ExtensionAware aware) {
        Response response = HttpUtil.get(new Request.Builder().url(MirrorUtil.getVersionManifests(aware)).build());
        try  {
            String json = response.body().string();
            return KessokuGradlePlugin.GSON.fromJson(json, VersionManifest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
