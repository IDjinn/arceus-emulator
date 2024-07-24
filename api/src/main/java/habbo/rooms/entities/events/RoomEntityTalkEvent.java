package habbo.rooms.entities.events;

import com.google.gson.JsonObject;
import core.events.Cancellable;
import core.events.Event;
import habbo.rooms.entities.IRoomEntity;
import utils.gson.GsonHelper;

import java.sql.Timestamp;
import java.util.Objects;

public final class RoomEntityTalkEvent implements Event, Cancellable {
    private final IRoomEntity entity;
    private final int emotion;
    private final int bubble;
    private final Timestamp timestamp;
    private String message;
    private boolean isCancelled;

    public RoomEntityTalkEvent(IRoomEntity entity, String message, final int emotion, final int bubble, Timestamp timestamp) {
        this.entity = entity;
        this.message = message;
        this.emotion = emotion;
        this.bubble = bubble;
        this.timestamp = timestamp;
    }

    public IRoomEntity entity() {
        return this.entity;
    }

    public String message() {
        return this.message;
    }

    public Timestamp timestamp() {
        return this.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity, this.message, this.timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RoomEntityTalkEvent) obj;
        return Objects.equals(this.entity, that.entity) &&
                Objects.equals(this.message, that.message) &&
                Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public String toString() {
        final var gson = GsonHelper.getGson();
        final var jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.entity.getName());
        jsonObject.addProperty("message", this.message);
        jsonObject.addProperty("emotion", this.emotion);
        jsonObject.addProperty("bubble", this.bubble);
        jsonObject.addProperty("timestamp", this.timestamp.getTime());
        return gson.toJson(jsonObject);
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public int emotion() {
        return this.emotion;
    }

    public int bubble() {
        return this.bubble;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
