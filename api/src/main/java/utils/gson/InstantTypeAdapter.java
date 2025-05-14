package utils.gson;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Instant;

public final class InstantTypeAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Instant.parse(json.getAsString());
        } catch (Exception e) {
            throw new JsonParseException("Error parsing Instant: " + json.getAsString(), e);
        }
    }
}