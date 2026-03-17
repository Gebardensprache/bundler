package band.kessoku.gradle.minecraft.manifest.version;

public record Logging(Client client) {

    public record Client(
            String argument,
            File file,
            String type
    ) {}

    public record File(
            String id,
            String sha1,
            long size,
            String url
    ) {}

}
