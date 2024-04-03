package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.NodeQueue;
import habbo.rooms.components.pathfinder.pool.PathfinderNodeFucked;
import stormpot.Allocator;
import stormpot.Slot;

public class NodeQueueAllocator implements Allocator<NodeQueue<PathfinderNodeFucked>> {

    @Override
    public NodeQueue<PathfinderNodeFucked> allocate(Slot slot) throws Exception {
        return new NodeQueue<PathfinderNodeFucked>(slot);
    }

    @Override
    public void deallocate(NodeQueue<PathfinderNodeFucked> pathfinderNode) throws Exception {
    }

}