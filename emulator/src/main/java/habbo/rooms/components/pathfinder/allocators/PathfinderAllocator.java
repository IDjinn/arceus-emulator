package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.PathfinderNode;
import stormpot.Allocator;
import stormpot.Slot;

public class PathfinderAllocator implements Allocator<PathfinderNode> {

    @Override
    public PathfinderNode allocate(Slot slot) throws Exception {
        return new PathfinderNode(slot);
    }

    @Override
    public void deallocate(PathfinderNode pathfinderNode) throws Exception {
    }

}