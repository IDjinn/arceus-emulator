package habbo.rooms.components.pathfinder.allocators;

import habbo.rooms.components.pathfinder.pool.NodeQueue;
import habbo.rooms.components.pathfinder.pool.PathfinderNode;
import stormpot.Allocator;
import stormpot.Slot;

public class NodeQueueAllocator implements Allocator<NodeQueue<PathfinderNode>> {

    @Override
    public NodeQueue<PathfinderNode> allocate(Slot slot) throws Exception {
        return new NodeQueue<PathfinderNode>(slot);
    }

    @Override
    public void deallocate(NodeQueue<PathfinderNode> pathfinderNode) throws Exception {
    }

}