package habbo.rooms.components.pathfinder.pool;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;
import utils.pathfinder.Position;

public class PathfinderNodeFucked implements Poolable, Comparable<PathfinderNodeFucked> {
    private final Slot slot;
    public Position position;
    public @Nullable PathfinderNodeFucked parentNode;
    @Getter
    @Setter
    public float gCosts;
    @Getter
    @Setter
    public float hCosts;
    @Getter
    @Setter
    private boolean free = true;

    public PathfinderNodeFucked(Slot slot) {
        this.slot = slot;
    }

    @Override
    public void release() {
        this.position = null;
        this.parentNode = null;
        this.gCosts = 0;
        this.hCosts = 0;
        this.free = true;
        this.slot.release(this);
    }

    public @Nullable Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.setFree(false);
    }

    public @Nullable PathfinderNodeFucked getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(@Nullable PathfinderNodeFucked parentNode) {
        this.parentNode = parentNode;
    }

    public float getFCosts() {
        return this.gCosts + this.hCosts;
    }

    @Override
    public int compareTo(@NotNull PathfinderNodeFucked otherNode) {
        return Float.compare(this.getFCosts(), otherNode.getFCosts());
    }
}