package benchmark.pathfinder;

import com.google.common.collect.MinMaxPriorityQueue;
import habbo.rooms.components.objects.items.IFloorObject;
import habbo.rooms.components.pathfinder.PathfinderNode;
import habbo.rooms.components.pathfinder.Position3d;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.*;


public class Pathfinder {

    private static final String MODEL_A = """
            xxxxxxxxxxxx
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxx000000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxx00000000
            xxxxxxxxxxxx
            xxxxxxxxxxxx
            """.trim();
    private static final int maxX = MODEL_A.split("\n")[0].length();
    private static final int maxY = MODEL_A.split("\n").length;

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


    private static boolean isDiagonal(Position a, Position b) {
        assert a != null && b != null;
        return Math.abs((short) a.getX() - (short) b.getX()) == 1
                && Math.abs((short) a.getY() - (short) b.getY()) == 1;
    }

    private static double getGCost(final double currentCost, final Position currentPosition, final Position nextPosition) {
        final var dz = Math.abs(nextPosition.getZ() - currentPosition.getZ());
        final var horizontalCost = isDiagonal(currentPosition, nextPosition) ? DiagonalCost : BasicCost;// TODO CHECK THIS
        final var verticalCost = VerticalCostFactor * dz;
        return horizontalCost + verticalCost;
    }

    private List<Position> reversePath(final PathfinderNode node) {
        final var path = new ArrayList<Position>();
        var current = node;
        while (current.parentNode != null) {
            path.add(new Position(current.getPosition().getX(), current.getPosition().getY(), current.getPosition().getZ()));
            current = current.parentNode;
        }
        Collections.reverse(path);
        return path;
    }


    private List<PathfinderNode> getNeighbors(
            final IFloorObject floorObject,
            final PathfinderNode current,
            final Position3d goal
    ) {
        final var neighbors = new ArrayList<PathfinderNode>();
        for (final var direction : diagonalDirections.values()) {

        }
        return neighbors;
    }


    @SuppressWarnings("UnstableApiUsage")
    public SequencedCollection<Position> tracePath(final Position start, final Position goal) {
        final MinMaxPriorityQueue<PathfinderNode> openSet = MinMaxPriorityQueue.maximumSize(256).create();
        final var closedSet = new HashSet<Position>();
        final var firstNode = new PathfinderNode(null);
        openSet.add(firstNode);

//        var step = 0;
//        final var mapSize = 192;
//        while (!openSet.isEmpty() && step++ < mapSize) {
//            final var current = openSet.poll();
//            if (current.getPosition().equals(goal))
//                return reversePath(current);
//
//            if (closedSet.contains(goal))
//                break;
//
//            closedSet.add(current.getPosition());
//            for (final var node : this.getNeighbors(current)) {
//                if (closedSet.contains(node.position)) continue;
//
//                final var tentativeGScore = (float) getGCost(current.getGCosts(), current.position, node.position);
//                if (tentativeGScore < node.getGCosts() || !openSet.contains(node)) {
//                    node.setParentNode(current);
//                    node.setGCosts(tentativeGScore);
//                    node.setHCosts((float) node.getPosition().distanceTo(goal));
//                    openSet.add(node);
//                }
//            }
//        }
        return Collections.emptyList();
    }
}

