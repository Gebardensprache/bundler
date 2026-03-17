package band.kessoku.gradle.minecraft.manifest.version;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public record Arguments(
        List<Argument> game,
        List<Argument> jvm,
        @SerializedName("default-user-jvm")
        List<Argument> defaultUserJvm
) {
    public record Argument(
            List<Rule> rules,
            Value value
    ) {}

    sealed interface Value permits StringValue, ListValue {}

    public record StringValue(String value) implements Value {}
    public record ListValue(List<String> value) implements Value {}

    public record Rule(
            String action,
            OS os,
            Features features
    ) {}

    public record OS(
            String name,
            String arch,
            VersionRange versionRange
    ) {}

    public record VersionRange(String max, String min) { }

    public record Features(
            boolean is_demo_user,
            boolean has_custom_resolution,
            boolean has_quick_plays_support,
            boolean is_quick_play_singleplayer,
            boolean is_quick_play_multiplayer,
            boolean is_quick_play_realms
    ) {}

    public static class ArgumentDeserializer implements JsonDeserializer<Argument> {
        @Override
        public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
                throws JsonParseException {

            if (json.isJsonPrimitive()) return new Argument(null, new StringValue(json.getAsString()));

            JsonObject obj = json.getAsJsonObject();

            List<Rule> rules = null;
            if (obj.has("rules")) rules = ctx.deserialize(obj.get("rules"), new TypeToken<List<Rule>>(){}.getType());

            JsonElement valueElement = obj.get("value");

            Value value;
            if (valueElement.isJsonArray()) {
                value = new ListValue(ctx.deserialize(valueElement, new TypeToken<List<String>>(){}.getType()));
            } else {
                value = new StringValue(valueElement.getAsString());
            }

            return new Argument(rules, value);
        }
    }
}
