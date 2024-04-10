package habbo.rooms.components.pathfinder;

import habbo.rooms.components.pathfinder.allocators.NodeQueueAllocator;
import habbo.rooms.components.pathfinder.allocators.NodesSetAllocator;
import habbo.rooms.components.pathfinder.allocators.PathfinderAllocator;
import habbo.rooms.components.pathfinder.pool.NodeQueue;
import habbo.rooms.components.pathfinder.pool.NodesSet;
import habbo.rooms.components.pathfinder.pool.PathfinderNodeFucked;
import stormpot.Expiration;
import stormpot.Pool;
import utils.pathfinder.Position;

import java.util.ArrayList;
import java.util.SequencedCollection;
import java.util.concurrent.TimeUnit;

public class PathUtil {
    private static PathUtil instance;
    public final SequencedCollection<Position> EmptyPath = new ArrayList<>();
    public final Pool<NodesSet<PathfinderNodeFucked>> Setpool;
    public final Pool<NodeQueue<PathfinderNodeFucked>> QueuePool;
    public final Pool<PathfinderNodeFucked> NodesPool;
    public PathUtil() {
        Setpool = Pool
                .from(new NodesSetAllocator())
                .setSize(40)
                .setExpiration(Expiration.after(2, TimeUnit.SECONDS))
                .setThreadFactory(Thread.ofVirtual().factory())
                .build();
        NodesPool = Pool
                .from(new PathfinderAllocator())
                .setSize(400)
                .setExpiration(Expiration.after(2, TimeUnit.SECONDS))
                .setThreadFactory(Thread.ofVirtual().factory())
                .build();
        QueuePool = Pool
                .from(new NodeQueueAllocator())
                .setExpiration(Expiration.after(2, TimeUnit.SECONDS))
                .setSize(40)
                .setThreadFactory(Thread.ofVirtual().factory())
                .build();
    }

    public static PathUtil getInstance() {
        if (instance == null) {
            instance = new PathUtil();
        }
        return instance;
    }

}
