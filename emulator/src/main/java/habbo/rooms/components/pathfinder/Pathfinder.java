package habbo.rooms.components.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.IRoom;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.*;

public class Pathfinder implements IPathfinder {
    private IRoom room;

    private static final int VerticalCostFactor = 22;
    private static final int DiagonalCost = 14;
    private static final int BasicCost = 10;
    private static final HashMap<Direction, Position> adjacentDirections = new HashMap<>();
    private static final HashMap<Direction, Position> diagonalDirections = new HashMap<Direction, Position>();

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

    private static float calculateHCost(Position a, Position b) {
        var dx = Math.abs(b.getX() - a.getX());
        var dy = Math.abs(b.getY() - a.getY());
        var dz = Math.abs(b.getZ() - a.getZ());
        var horizontalCost = BasicCost * (dx + dy) + (DiagonalCost - 2 * BasicCost) * Math.min(dx, dy);
        var verticalCost = VerticalCostFactor * dz;
        return (float) (horizontalCost + verticalCost);
    }

    private static float calculateGCost(Position a, Position b, boolean diagonalEnabled) {
        assert a != null && b != null;

        var dz = Math.abs(b.getZ() - a.getZ());
        var horizontalCost = is_diagonal(a, b) ? (diagonalEnabled ? DiagonalCost : Float.POSITIVE_INFINITY) : BasicCost;
        var verticalCost = VerticalCostFactor * dz;
        return (float) (horizontalCost + verticalCost);
    }

    private static boolean is_diagonal(Position a, Position b) {
        assert a != null && b != null;
        return Math.abs((short) a.getX() - (short) b.getX()) == 1
                && Math.abs((short) a.getY() - (short) b.getY()) == 1;
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

    @SuppressWarnings("UnstableApiUsage") 
    @Override
    public SequencedCollection<Position> tracePath(final Position start, final Position goal) {
        final MinMaxPriorityQueue<PathfinderNode> openSet = MinMaxPriorityQueue.maximumSize(256).create();
        final var closedSet = new HashSet<Position>();
        final var firstNode = new PathfinderNode(start);
        openSet.add(firstNode);

        var step = 0;
        final var mapSize = this.getRoom().getGameMap().getMapSize();
        while (!openSet.isEmpty() && step++ < mapSize) {
            final var current = openSet.poll();
            if (current.getPosition().equals(goal))
                return reversePath(current);

            if (closedSet.contains(goal))
                break;

            closedSet.add(current.getPosition());
            for (final var node : getNeighbors(current)) {
                if (closedSet.contains(node)) continue;

                final var tentativeGScore = (float) (current.getGCosts() + current.getPosition().distanceTo(node.getPosition()));
                if (tentativeGScore < node.getGCosts() || !openSet.contains(node)) {
                    node.setParentNode(current);
                    node.setGCosts(tentativeGScore);
                    node.setHCosts((float) node.getPosition().distanceTo(goal));
                    openSet.add(node);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Position> reversePath(final PathfinderNode node) {
        final var path = new ArrayList<Position>();
        var current = node;
        while (current.parentNode != null) {
            path.add(current.getPosition());
            current = current.parentNode;
        }
        Collections.reverse(path);
        return path;
    }

    private List<PathfinderNode> getNeighbors(final PathfinderNode current) {
        final var neighbors = new ArrayList<PathfinderNode>();
        for (final var direction : diagonalDirections.values()) {
            final var neighborPosition = current.getPosition().add(direction);

            if (!this.getRoom().getGameMap().isValidCoordinate(neighborPosition)) continue;

            final var neighbor = new PathfinderNode(neighborPosition);
            neighbor.parentNode = current;
            neighbors.add(neighbor);
        }
        return neighbors;
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
