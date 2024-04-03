package habbo.rooms.components.pathfinder.pool;

import stormpot.Poolable;
import stormpot.Slot;

import java.util.HashSet;

public class NodesSet<T extends Poolable> extends HashSet<T> implements Poolable, AutoCloseable {

    private final Slot slot;

    public NodesSet(Slot slot) {

        this.slot = slot;
    }

    @Override
    public void release() {
        this.slot.release(this);
    }

    public void fullRelease() {
        for (var item : this) {
            var node = (PathfinderNodeFucked) item;
            if (node.isFree())
                continue;
            node.release();
        }
        this.clear();
        this.release();
    }

    @Override
    public void close() throws Exception {
        this.release();
    }
}
