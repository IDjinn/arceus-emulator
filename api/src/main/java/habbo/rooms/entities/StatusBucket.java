package habbo.rooms.entities;

import org.jetbrains.annotations.Nullable;

public class StatusBucket {
    private final RoomEntityStatus status;
    @Nullable
    private String value;
    private int ticks;

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
        return status;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void decrementTick() {
        this.ticks--;
    }
}