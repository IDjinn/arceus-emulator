package habbo.rooms.entities.status;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class StatusBucket {
    private final RoomEntityStatus status;
    @Nullable
    private String value;
    private Optional<Integer> ticks;

    public StatusBucket(RoomEntityStatus status) {
        this.status = status;
        this.value = null;
        this.ticks = Optional.empty();
    }
    public StatusBucket(RoomEntityStatus status, @Nullable String value) {
        this.status = status;
        this.value = value;
        this.ticks = Optional.empty();
    }

    public StatusBucket(RoomEntityStatus status, @Nullable String value, int ticks) {
        this.status = status;
        this.value = value;
        this.ticks = Optional.of(ticks);
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

    public Optional<Integer> getTicks() {
        return this.ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = Optional.of(ticks);
    }

    public void decrementTick() {
        this.ticks.ifPresent(tick -> this.setTicks(tick - 1));
    }
}