package habbo.rooms.components.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.IRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stormpot.Allocator;
import stormpot.Pool;
import stormpot.Slot;
import stormpot.Timeout;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Pathfinder implements IPathfinder {
    private IRoom room;

    private static final int VerticalCostFactor = 22;
    private static final int DiagonalCost = 14;
    private static final int BasicCost = 10;
    private static final HashMap<Direction, Position> adjacentDirections = new HashMap<>();
    private static final HashMap<Direction, Position> diagonalDirections = new HashMap<>();

    static {
        adjacentDirections.put(Direction.North, new Position(1, 0, 0));
        adjacentDirections.put(Direction.East, new Position(0, 1, 0));
        adjacentDirections.put(Direction.South, new Position(-1, 0, 0));
        adjacentDirections.put(Direction.West, new Position(0, -1, 0));

        diagonalDirections.put(Direction.NorthEast, new Position(1, 1, 0));
        diagonalDirections.put(Direction.NorthWest, new Position(-1, 1, 0));
        diagonalDirections.put(Direction.SouthEast, new Position(1, -1, 0));
        diagonalDirections.put(Direction.SouthWest, new Position(-1, -1, 0));
        diagonalDirections.putAll(adjacentDirections);
    }

    private final Logger logger = LogManager.getLogger();
    private final Pool<PathfinderNode> nodePool = Pool
            .from(new PathfinderNodeAllocator())
            .setThreadFactory(Thread.ofVirtual().factory())
            .setSize(1000)
            .build();

    private List<Position> reversePath(final PathfinderNode node) {
        final var path = new ArrayList<Position>();
        var current = node;
        while (current.parentNode != null) {
            path.add(current.getPosition());
            var aux = current.parentNode;
            current.release();
            current = aux;
        }
        Collections.reverse(path);
        return path;
    }
    
    @Override
    public void init(IRoom room) {
        this.room = room;
    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isEnabled3d() {
        return false;
    }

    @Override
    public void setEnabled3d(boolean enabled3d) {

    }

    private static boolean isDiagonal(Position a, Position b) {
        assert a != null && b != null;
        return Math.abs((short) a.getX() - (short) b.getX()) == 1
                && Math.abs((short) a.getY() - (short) b.getY()) == 1;
    }

    private List<PathfinderNode> getNeighbors(final PathfinderNode current) {
        final var neighbors = new ArrayList<PathfinderNode>();
        for (final var direction : diagonalDirections.values()) {
            try {
                final var neighborPosition = current.getPosition().add(direction);

                if (!this.getRoom().getGameMap().isValidCoordinate(neighborPosition)) continue;

                final var neighbor = this.nodePool.claim(new Timeout(500, TimeUnit.MILLISECONDS));
                neighbor.setPosition(neighborPosition);
                neighbor.parentNode = current;
                neighbors.add(neighbor);
            } catch (Exception e) {
                this.logger.error("error while getting neighbors for room {}", this.getRoom().getData().getId(), e);
            }
        }
        return neighbors;
    }

    @SuppressWarnings("UnstableApiUsage") 
    @Override
    public SequencedCollection<Position> tracePath(final Position start, final Position goal) {
        final MinMaxPriorityQueue<PathfinderNode> openSet = MinMaxPriorityQueue.maximumSize(256).create();
        final var closedSet = new HashSet<Position>();
        final var mapSize = this.getRoom().getGameMap().getMapSize();

        var step = 0;
        var closed = 0;
        var open = 0;
        PathfinderNode startNode = null;
        try {
            startNode = nodePool.claim(new Timeout(1, TimeUnit.SECONDS));
            startNode.setPosition(start);
            openSet.add(startNode);
            while (!openSet.isEmpty() && step++ < mapSize) {
                final var current = openSet.poll();
                if (current.getPosition().equals(goal))
                    return reversePath(current);

                if (closedSet.contains(goal))
                    break;

                closedSet.add(current.getPosition());
                for (final var node : this.getNeighbors(current)) {
                    if (closedSet.contains(node.position)) {
                        closed++;
                        node.release();
                        continue;
                    }

                    final var tentativeGScore = (float) getGCost(current.getGCosts(), current.position, node.position, true);
                    if (tentativeGScore < node.getGCosts() || !openSet.contains(node)) {
                        open++;
                        node.setParentNode(current);
                        node.setGCosts(tentativeGScore);
                        node.setHCosts((float) node.getPosition().distanceTo(goal));
                        openSet.add(node);
                    }
                }
            }
            return Collections.emptyList();
        } catch (Exception e) {
            this.logger.error("error while tracing path for room {}", this.getRoom().getData().getId(), e);
            return Collections.emptyList();
        } finally {
            if (startNode != null) startNode.release();
            for (var openNode : openSet) {
                openNode.release();
            }
        }
    }

    private static double getGCost(final double currentCost, final Position currentPosition,
                                   final Position nextPosition, boolean diagonalEnabled) {
        final var dz = Math.abs(nextPosition.getZ() - currentPosition.getZ());
        final var horizontalCost = BasicCost;
        final var verticalCost = VerticalCostFactor * dz;
        return horizontalCost + verticalCost;
    }

    private class PathfinderNodeAllocator implements Allocator<PathfinderNode> {

        @Override
        public PathfinderNode allocate(final Slot slot) throws Exception {
            return new PathfinderNode(slot);
        }

        @Override
        public void deallocate(final PathfinderNode poolable) throws Exception {

        }
    }

    @Override
    public HashMap<Direction, Position> getAdjacentDirections() {
        return adjacentDirections;
    }

    @Override
    public HashMap<Direction, Position> getDiagonalDirections() {
        return diagonalDirections;
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }
}
