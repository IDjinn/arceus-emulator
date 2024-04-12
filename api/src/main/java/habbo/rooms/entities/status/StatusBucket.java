package habbo.rooms.entities.status;

import org.jetbrains.annotations.Nullable;

public class StatusBucket {
    private final RoomEntityStatus status;
    @Nullable
    private String value;
    private int ticks;

    public StatusBucket(RoomEntityStatus status) {
        this.status = status;
        this.value = null;
    }
    public StatusBucket(RoomEntityStatus status, @Nullable String value) {
        this.status = status;
        this.value = value;
    }

    public StatusBucket(RoomEntityStatus status, @Nullable String value, int ticks) {
        this.status = status;
        this.value = value;
        this.ticks = ticks;
    }

    public RoomEntityStatus getStatus() {
        return this.status;
    }

    @Nullable
    public String getValue() {
        return this.value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public int getTicks() {
        return this.ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void decrementTick() {
        this.ticks--;
    }
}