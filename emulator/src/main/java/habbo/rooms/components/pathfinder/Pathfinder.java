package habbo.rooms.components.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.IRoom;
import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.IFloorObject;
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

    private static final int COST_BASE_VERTICAL = 22;
    private static final int COST_BASE_DIAGONAL = 14;
    private static final int COST_BASE = 10;
    private static final double PLAYER_CLIMB_MAX_HEIGHT = 1.5d;
    private static final double MIN_WALKABLE_HEIGHT_BETWEEN_TILES = 2.0d;
    
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
        var horizontalCost = isDiagonal(currentPosition, nextPosition) ? COST_BASE_DIAGONAL : COST_BASE;
        var verticalCost = dz == 0 ? COST_BASE_VERTICAL : dz;
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
        return COST_BASE_VERTICAL * (dx + dy) + (COST_BASE_DIAGONAL - 2 * COST_BASE) * Math.min(dx, dy);
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
            final PathfinderNode from,
            final Position3d goal
    ) {
        final var neighbors = new ArrayList<PathfinderNode>();
        for (final var direction : diagonalDirections.values()) {
            try {
                final var to = this.positionPool.claim(new Timeout(500, TimeUnit.MILLISECONDS));
                to.setX(from.getPosition().getX() + direction.getX());
                to.setY(from.getPosition().getY() + direction.getY());
                if (!this.getRoom().getGameMap().isValidCoordinate(to.getX(), to.getY())) {
                    to.release();
                    continue;
                }

                final var targetTile = this.getRoom().getGameMap().getTile(to.getX(), to.getY());
                final var tileMetadataList = targetTile.getMetadata()
                        .stream()
                        .sorted(Comparator.comparingDouble(m -> m.getHeight().isEmpty() ? Float.POSITIVE_INFINITY : m.getHeight().get() - from.getPosition().getZ()))
                        .toList();

                if (tileMetadataList.isEmpty()) {
                    to.release();
                    continue;
                }

                if (tileMetadataList.size() == 1) {
                    final var metadata = tileMetadataList.getFirst();
                    if (metadata.getHeight().isEmpty()) {
                        to.release();
                        continue;
                    }

                    final var neighbor = this.createNode(to, metadata);
                    neighbor.parentNode = from;
                    neighbors.add(neighbor);
                    continue;
                }

                final var isGoal =
                        targetTile.getPosition().getX() == goal.getX() && targetTile.getPosition().getY() == goal.getY();
                var release = true;
                for (int i = tileMetadataList.size() - 1; i >= 0; i--) {
                    final var metadata = tileMetadataList.get(i);
                    if (metadata.getHeight().isEmpty()) continue;

                    final var bottomMetadata = i - 1 >= 0 ? tileMetadataList.get(i) : null;
                    final var topMetadata = i + 1 < tileMetadataList.size() ? tileMetadataList.get(i) : null;

                    final var tooHighToClimb = topMetadata != null && topMetadata.getHeight().isPresent()
                            && topMetadata.getHeight().get() + from.getPosition().getZ() > PLAYER_CLIMB_MAX_HEIGHT;
                    final var tooLowToFall = bottomMetadata == null || bottomMetadata.getHeight().isPresent()
                            && bottomMetadata.getHeight().get() < to.getZ() - MIN_WALKABLE_HEIGHT_BETWEEN_TILES;
                    if (tooHighToClimb || tooLowToFall) continue;

                    final var neighbor = this.createNode(to, metadata);
                    neighbor.parentNode = from;
                    neighbors.add(neighbor);
                    release = false;
                }

                if (release) to.release();
            } catch (Exception e) {
                this.logger.error("error while getting neighbors for room {}",
                        this.getRoom().getData().getId(), e);
            }
        }
        return neighbors;
    }

    private PathfinderNode createNode(final Position3d position3d, final ITileMetadata tileMetadata) throws InterruptedException {
        final var neighbor = this.nodePool.claim(new Timeout(500, TimeUnit.MILLISECONDS));
        position3d.setZ(tileMetadata.getHeight().get().floatValue()); // TODO MAYBE FLOOR_OBJECT METHOD?
        neighbor.setPosition(position3d);
        neighbor.setHCosts(Float.POSITIVE_INFINITY);
        return neighbor;
    }

    private static class PathfinderNodeAllocator implements Allocator<PathfinderNode> {

        @Override
        public PathfinderNode allocate(final Slot slot) throws Exception {
            return new PathfinderNode(slot);
        }

        @Override
        public void deallocate(final PathfinderNode poolable) throws Exception {

        }
    }

    private static class Position3dAllocator implements Allocator<Position3d> {
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
