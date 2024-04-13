package habbo.rooms.components.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;

public class PathfinderNode implements Comparable<PathfinderNode>, Poolable {
    private final Slot slot;
    public Position3d position;
    public @Nullable PathfinderNode parentNode;
    public float gCosts;
    public float hCosts;

    public PathfinderNode(Slot slot) {
        this.slot = slot;
    }


    public Position3d getPosition() {
        return this.position;
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

    public float getGCosts() {
        return this.gCosts;
    }

    public void setGCosts(float gCosts) {
        this.gCosts = gCosts;
    }

    public float getHCosts() {
        return this.hCosts;
    }

    public void setHCosts(float hCosts) {
        this.hCosts = hCosts;
    }

    public void setPosition(final Position3d position) {
        this.position = position;
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