package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.PathfinderNodeFucked;
import stormpot.Allocator;
import stormpot.Slot;

public class PathfinderAllocator implements Allocator<PathfinderNodeFucked> {

    @Override
    public PathfinderNodeFucked allocate(Slot slot) throws Exception {
        return new PathfinderNodeFucked(slot);
    }

    @Override
    public void deallocate(PathfinderNodeFucked pathfinderNode) throws Exception {
    }

}