package band.kessoku.gradle.utils;

import lombok.experimental.UtilityClass;
import okhttp3.*;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.function.Consumer;

@UtilityClass
public class HttpUtil {

    public static final OkHttpClient client = new OkHttpClient();

    public static Response get(Request request) {
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void get(Request request, Consumer<Response> responseConsumer) {
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    throw new RuntimeException(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    responseConsumer.accept(response);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
