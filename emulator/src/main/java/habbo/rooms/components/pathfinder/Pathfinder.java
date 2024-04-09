package habbo.rooms.components.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.IRoom;
import habbo.rooms.components.gamemap.IRoomGameMap;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SequencedCollection;

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
    public SequencedCollection<Position> tracePath(IRoomGameMap gameMap, Position start, Position goal) {
        assert start != goal : "start != goal; should be checked before call this method";
        if (start.equals(goal))
            return PathUtil.getInstance().EmptyPath;

        final MinMaxPriorityQueue<PathfinderNode> openSet = MinMaxPriorityQueue.maximumSize(gameMap.getMapSize()).create();
        openSet.add(new PathfinderNode(start));
        var closedSet = new HashSet<Position>(gameMap.getMapSize());

        var size = 0;
        while (!openSet.isEmpty()) {
            if (closedSet.size() > gameMap.getMapSize() || openSet.size() > gameMap.getMapSize()) // something went wrong with pathfinder logic. prevent overflow
                return PathUtil.getInstance().EmptyPath;

            if (closedSet.contains(goal))
                return PathUtil.getInstance().EmptyPath;

            var current = openSet.poll();
            assert current != null;
            closedSet.add(current.getPosition());
            openSet.remove(current);

            if (current.getPosition().equals(goal)) {
                var path = new ArrayList<Position>(size);
                var tile = current;
                while (tile != null) {
                    if (tile.getPosition().equals(start)) break;

                    path.add(tile.getPosition());
                    tile = tile.getParentNode();
                }
                return path.reversed();
            }

            for (var neighbor : getNeighbors(gameMap, current.getPosition(), closedSet)) {
                neighbor.setParentNode(current);
                if (!openSet.contains(neighbor)) {
                    neighbor.setGCosts(calculateGCost(current.getPosition(), neighbor.getPosition(), true));
                    neighbor.setHCosts(calculateHCost(neighbor.getPosition(), goal));
                    openSet.add(neighbor);
                    size++;
                } else if (BasicCost + neighbor.getGCosts() < neighbor.getFCosts()) {
                    neighbor.setGCosts(BasicCost);
                    size++;
                }
            }
        }


        return PathUtil.getInstance().EmptyPath;
    }

    private ArrayList<PathfinderNode> getNeighbors(IRoomGameMap gameMap, Position position, HashSet<Position> closedSet) {
        assert position != null;
        var neighborSet = new ArrayList<PathfinderNode>(diagonalDirections.size());
        for (var direction : diagonalDirections.values()) {
            var neighborPosition = position.add(direction);

            if (!gameMap.isValidCoordinate(neighborPosition)) continue;
            if (closedSet.contains(neighborPosition)) continue;

            neighborSet.add(new PathfinderNode(neighborPosition));
        }
        return neighborSet;
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
