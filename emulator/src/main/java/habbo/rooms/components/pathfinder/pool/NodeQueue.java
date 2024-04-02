package habbo.rooms.components.pathfinder.pool;

import stormpot.Poolable;
import stormpot.Slot;

import java.util.PriorityQueue;

public class NodeQueue<T extends Poolable> extends PriorityQueue<T> implements Poolable {
    private final Slot slot;

    public NodeQueue(Slot slot) {
        this.slot = slot;
    }

    @Override
    public void release() {
        this.slot.release(this);
    }

    public void fullRelease() {
        for (var item : this) {
            var node = (PathfinderNode) item;
            if (node.isFree())
                continue;
            node.release();
        }
        this.release();
    }
}