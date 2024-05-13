package habbo.rooms.entities.events;

import core.events.Cancellable;
import core.events.Event;
import habbo.rooms.entities.IRoomEntity;
import utils.pathfinder.Position;

import java.util.Objects;

public final class RoomEntityWalkEvent implements Event, Cancellable {
    private final IRoomEntity entity;
    private final Position oldPosition;
    private final Position newPosition;
    private boolean isCancelled;

    public RoomEntityWalkEvent(IRoomEntity entity, Position oldPosition, Position newPosition) {
        this.entity = entity;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public IRoomEntity entity() {
        return this.entity;
    }

    public Position oldPosition() {
        return this.oldPosition;
    }

    public Position newPosition() {
        return this.newPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity, this.oldPosition, this.newPosition);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RoomEntityWalkEvent) obj;
        return Objects.equals(this.entity, that.entity) &&
                Objects.equals(this.oldPosition, that.oldPosition) &&
                Objects.equals(this.newPosition, that.newPosition);
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public boolean isWarp() {
        return this.oldPosition.distanceTo(this.newPosition) > 1;
    }
}
