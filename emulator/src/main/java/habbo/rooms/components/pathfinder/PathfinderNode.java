package habbo.rooms.components.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.pathfinder.Position;

import java.util.concurrent.atomic.AtomicInteger;

public class PathfinderNode implements Comparable<PathfinderNode> {
    public static final AtomicInteger total = new AtomicInteger(0);
    public final Position position;
    public @Nullable PathfinderNode parentNode;
    public float gCosts;
    public float hCosts;

    public PathfinderNode(Position position) {
        this.position = position;
        total.incrementAndGet();
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
}