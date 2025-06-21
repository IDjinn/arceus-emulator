package habbo.rooms.components.pathfinder;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import stormpot.Poolable;
import stormpot.Slot;
import utils.pathfinder.Position;

import java.util.Objects;

public class Position3d implements Poolable, Comparable<Position3d> {

    private final Slot slot;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;
    @Getter
    @Setter
    private float z;


    public Position3d(Slot slot) {
        this.slot = slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Position3d other) {
            return this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ();
        }
        return false;
    }

    public boolean tileEquals(final Position3d other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }

    @Override
    public int compareTo(@NotNull final Position3d o) {
        return Float.compare(this.getZ(), o.getZ());
    }

    @Override
    public void release() {
        this.slot.release(this);
    }

    public void setFromPosition(final Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = (float) position.getZ();
    }

    public void setFromPosition(final Position3d position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = (float) position.getZ();
    }

    public double distanceTo(Position pos) {
        return Math.sqrt(Math.pow((this.getX() - pos.getX()), 2) + Math.pow((this.getY() - pos.getY()), 2));
    }

    public double distanceTo(Position3d pos) {
        return Math.sqrt(Math.pow((this.getX() - pos.getX()), 2) + Math.pow((this.getY() - pos.getY()), 2));
    }
}