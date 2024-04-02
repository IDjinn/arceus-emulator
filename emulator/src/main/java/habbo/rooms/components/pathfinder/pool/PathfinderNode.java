package habbo.rooms.components.pathfinder.pool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;
import utils.Position;

public class PathfinderNode implements Poolable, Comparable<PathfinderNode> {
    private final Slot slot;
    public Position position;
    public @Nullable PathfinderNode parentNode;
    public float fCosts;
    public float gCosts;
    public float hCosts;
    private boolean free = true;

    public PathfinderNode(Slot slot) {
        this.slot = slot;
    }

    @Override
    public void release() {
        this.position = null;
        this.parentNode = null;
        this.fCosts = 0;
        this.gCosts = 0;
        this.hCosts = 0;
        this.free = true;
        this.slot.release(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.setFree(false);
    }

    public @Nullable PathfinderNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(@Nullable PathfinderNode parentNode) {
        this.parentNode = parentNode;
    }

    public float getFCosts() {
        return gCosts + hCosts;
    }

    public float getGCosts() {
        return gCosts;
    }

    public void setGCosts(float gCosts) {
        this.gCosts = gCosts;
    }

    public float getHCosts() {
        return hCosts;
    }

    public void setHCosts(float hCosts) {
        this.hCosts = hCosts;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean isOpen) {
        this.free = isOpen;
    }

    @Override
    public int compareTo(@NotNull PathfinderNode o) {
        if (this.fCosts < o.fCosts) {
            return -1;
        } else if (this.fCosts > o.fCosts) {
            return 1;
        } else {
            return 0;
        }
    }
}