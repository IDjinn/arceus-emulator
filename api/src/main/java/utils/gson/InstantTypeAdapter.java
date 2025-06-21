package utils.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public final class InstantTypeAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    @Override
    public JsonElement serialize(final Instant src, final Type typeOfSrc, final JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Instant deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        try {
            return Instant.parse(json.getAsString());
        } catch (Exception e) {
            throw new JsonParseException("Error parsing Instant: " + json.getAsString(), e);
        }
    }
}
