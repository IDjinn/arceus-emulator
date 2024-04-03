package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.NodesSet;
import habbo.rooms.components.pathfinder.pool.PathfinderNodeFucked;
import stormpot.Allocator;
import stormpot.Slot;

public class NodesSetAllocator implements Allocator<NodesSet<PathfinderNodeFucked>> {

    @Override
    public NodesSet<PathfinderNodeFucked> allocate(Slot slot) throws Exception {
        return new NodesSet<PathfinderNodeFucked>(slot);
    }

    @Override
    public void deallocate(NodesSet<PathfinderNodeFucked> pathfinderNode) throws Exception {
    }

}