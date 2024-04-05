package utils.gson;

import com.google.gson.Gson;

public class GsonHelper {
    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }
}
