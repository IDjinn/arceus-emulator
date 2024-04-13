package habbo.rooms.components.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.IFloorObject;
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

    private static final int VerticalBasicCost = 22;
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
    private final Pool<Position3d> positionPool = Pool
            .from(new Position3dAllocator())
            .setThreadFactory(Thread.ofVirtual().factory())
            .setSize(10_000)
            .build();

    private static boolean isDiagonal(Position a, Position b) {
        assert a != null && b != null;
        return Math.abs((short) a.getX() - (short) b.getX()) == 1
                && Math.abs((short) a.getY() - (short) b.getY()) == 1;
    }

    private static double getHCost(
            final Position currentPosition,
            final Position nextPosition,
            final double objectHeight) {
        var dz = Math.abs(currentPosition.getZ() - nextPosition.getZ());
        var horizontalCost = isDiagonal(currentPosition, nextPosition) ? DiagonalCost : BasicCost;
        var verticalCost = dz == 0 ? VerticalBasicCost : dz;
        return horizontalCost + verticalCost;
    }

    private static double getGCost(
            final double currentGCost,
            final Position3d currentPosition,
            final Position3d nextPosition,
            final double objectHeight
    ) {
        var dx = Math.abs(currentPosition.getX() - nextPosition.getX());
        var dy = Math.abs(currentPosition.getY() - nextPosition.getY());
        return BasicCost * (dx + dy) + (DiagonalCost - 2 * BasicCost) * Math.min(dx, dy);
    }

    private List<Position> reversePath(final PathfinderNode node) {
        final var path = new ArrayList<Position>();
        var current = node;
        while (current.parentNode != null) {
            path.add(new Position(current.getPosition().getX(), current.getPosition().getY(), current.getPosition().getZ()));
            var aux = current.parentNode;
            current.release();
            current = aux;
        }
        Collections.reverse(path);
        return path;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public SequencedCollection<Position> tracePath(
            final IFloorObject floorObject,
            final Position start,
            final Position goal
    ) {
        final MinMaxPriorityQueue<PathfinderNode> openSet = MinMaxPriorityQueue.maximumSize(256).create();
        final var closedSet = new HashSet<Position3d>();
        final var mapSize = this.getRoom().getGameMap().getMapSize();

        var step = 0;
        PathfinderNode startNode = null;
        Position3d startPosition = null;
        Position3d goalPosition = null;
        try {
            startNode = this.nodePool.claim(new Timeout(1, TimeUnit.SECONDS));
            startPosition = this.positionPool.claim(new Timeout(1, TimeUnit.SECONDS));
            goalPosition = this.positionPool.claim(new Timeout(1, TimeUnit.SECONDS));
            goalPosition.setFromPosition(goal);
            startPosition.setFromPosition(start);
            startNode.setPosition(startPosition);
            openSet.add(startNode);

            while (!openSet.isEmpty() && step++ < mapSize) {
                final var current = openSet.poll();
                assert current != null;
                if (current.getPosition().tileEquals(goalPosition))
                    return this.reversePath(current);

                if (closedSet.contains(goalPosition) || openSet.size() > mapSize || closedSet.size() > mapSize)
                    break;

                closedSet.add(current.getPosition());
                for (final var node : this.getNeighbors(floorObject, current, goalPosition)) {
                    if (closedSet.contains(node.position)) {
                        node.release();
                        continue;
                    }

                    final var tentativeGScore = (float) getGCost(current.getGCosts(), current.position, node.position, floorObject.getHeight());
                    if (tentativeGScore < node.getGCosts() || !openSet.contains(node)) {
                        node.setParentNode(current);
                        node.setGCosts(tentativeGScore);
                        node.setHCosts((float) node.getPosition().distanceTo(goalPosition));
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
//            if (startPosition != null) startPosition.release();
            if (goalPosition != null) goalPosition.release();
            for (var openNode : openSet) {
                openNode.release();
            }
            for (var closed : closedSet) {
                closed.release();
            }
        }
    }

    private List<PathfinderNode> getNeighbors(
            final IFloorObject floorObject,
            final PathfinderNode current,
            final Position3d goal
    ) {
        final var neighbors = new ArrayList<PathfinderNode>();
        for (final var direction : diagonalDirections.values()) {
            try {
                final var neighborPosition = positionPool.claim(new Timeout(500, TimeUnit.MILLISECONDS));
                neighborPosition.setX(current.getPosition().getX() + direction.getX());
                neighborPosition.setY(current.getPosition().getY() + direction.getY());
                if (!this.getRoom().getGameMap().isValidCoordinate(neighborPosition.getX(), neighborPosition.getY()))
                    continue;

                for (var metadata : this.getRoom().getGameMap().getMetadataAt(neighborPosition.getX(), neighborPosition.getY(), floorObject.getHeight())) {
                    if (metadata.getHeight().isEmpty()) continue;
                    if (!floorObject.canSlideTo(metadata)) continue;

                    final var neighbor = this.nodePool.claim(new Timeout(500, TimeUnit.MILLISECONDS));
                    neighborPosition.setZ(metadata.getHeight().get().floatValue()); // TODO MAYBE FLOOR_OBJECT METHOD?
                    neighbor.setPosition(neighborPosition);
                    neighbor.parentNode = current;
                    neighbor.setHCosts(Float.POSITIVE_INFINITY);
                    neighbors.add(neighbor);
                    break;
                }
            } catch (Exception e) {
                this.logger.error("error while getting neighbors for room {}",
                        this.getRoom().getData().getId(), e);
            }
        }
        return neighbors;
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

    private class Position3dAllocator implements Allocator<Position3d> {
        @Override
        public Position3d allocate(final Slot slot) throws Exception {
            return new Position3d(slot);
        }

        @Override
        public void deallocate(final Position3d poolable) throws Exception {

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
