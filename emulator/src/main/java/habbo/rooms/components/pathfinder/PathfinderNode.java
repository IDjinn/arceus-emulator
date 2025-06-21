package habbo.rooms.components.pathfinder;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;

public class PathfinderNode implements Comparable<PathfinderNode>, Poolable {
    private final Slot slot;
    @Getter
    @Setter
    public Position3d position;
    public @Nullable PathfinderNode parentNode;
    @Getter
    @Setter
    public float gCosts;
    @Getter
    @Setter
    public float hCosts;

    public PathfinderNode(Slot slot) {
        this.slot = slot;
    }


    public @Nullable PathfinderNode getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(@Nullable PathfinderNode parentNode) {
        this.parentNode = parentNode;
    }

    public float getFCosts() {
        return this.gCosts + this.hCosts;
    }

    @Override
    public void release() {
        this.position = null;
        this.parentNode = null;
        this.gCosts = 0f;
        this.hCosts = 0f;
        this.slot.release(this);
    }

    @Override
    public int compareTo(@NotNull PathfinderNode otherNode) {
        return Float.compare(this.getFCosts(), otherNode.getFCosts());
    }
}