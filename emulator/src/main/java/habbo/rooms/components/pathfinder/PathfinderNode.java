package habbo.rooms.components.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.pathfinder.Position;

public class PathfinderNode implements Comparable<PathfinderNode> {
    public final Position position;
    public @Nullable PathfinderNode parentNode;
    public float gCosts;
    public float hCosts;

    public PathfinderNode(Position position) {
        this.position = position;
    }


    public Position getPosition() {
        return position;
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