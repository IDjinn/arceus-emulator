package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.NodesSet;
import habbo.rooms.components.pathfinder.pool.PathfinderNode;
import stormpot.Allocator;
import stormpot.Slot;

public class NodesSetAllocator implements Allocator<NodesSet<PathfinderNode>> {

    @Override
    public NodesSet<PathfinderNode> allocate(Slot slot) throws Exception {
        return new NodesSet<PathfinderNode>(slot);
    }

    @Override
    public void deallocate(NodesSet<PathfinderNode> pathfinderNode) throws Exception {
    }

}