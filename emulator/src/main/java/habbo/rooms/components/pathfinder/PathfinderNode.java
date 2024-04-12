package habbo.rooms.components.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;
import utils.pathfinder.Position;

public class PathfinderNode implements Comparable<PathfinderNode>, Poolable {
    private final Slot slot;
    public Position position;
    public @Nullable PathfinderNode parentNode;
    public float gCosts;
    public float hCosts;

    public PathfinderNode(Slot slot) {
        this.slot = slot;
    }


    public Position getPosition() {
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

    @Override
    public int compareTo(@NotNull PathfinderNode otherNode) {
        if (this.getFCosts() < otherNode.getFCosts()) {
            return -1;
        } else if (this.getFCosts() > otherNode.getFCosts()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void release() {
        this.position = null;
        this.parentNode = null;
        this.gCosts = 0f;
        this.hCosts = 0f;
        this.slot.release(this);
    }

    public void setPosition(final Position position) {
        this.position = position;
    }
}