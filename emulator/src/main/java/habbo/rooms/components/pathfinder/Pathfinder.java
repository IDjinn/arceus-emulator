package habbo.rooms.components.pathfinder;

import habbo.rooms.IRoom;
import habbo.rooms.components.gamemap.IGameMap;
import habbo.rooms.components.pathfinder.pool.NodeQueue;
import habbo.rooms.components.pathfinder.pool.NodesSet;
import habbo.rooms.components.pathfinder.pool.PathfinderNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stormpot.Timeout;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SequencedCollection;
import java.util.concurrent.TimeUnit;

public class Pathfinder implements IPathfinder {
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

    private final IRoom room;
    private Logger logger = LogManager.getLogger();


    public Pathfinder(IRoom room) {
        this.room = room;
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
        var dz = Math.abs(b.getZ() - a.getZ());
        var horizontalCost = is_diagonal(a, b) ? (diagonalEnabled ? DiagonalCost : Float.POSITIVE_INFINITY) : BasicCost;
        var verticalCost = VerticalCostFactor * dz;
        return (float) (horizontalCost + verticalCost);
    }

    private static boolean is_diagonal(Position a, Position b) {
        return Math.abs((short) a.getX() - (short) b.getX()) == 1
                && Math.abs((short) a.getY() - (short) b.getY()) == 1;
    }

    @Override
    public boolean isEnabled3d() {
        return false;
    }

    @Override
    public void setEnabled3d(boolean enabled3d) {

    }

    @Override
    public HashMap<Direction, Position> getDiagonalDirections() {
        return diagonalDirections;
    }

    @Override
    public HashMap<Direction, Position> getAdjacentDirections() {
        return adjacentDirections;
    }

    @Override
    public SequencedCollection<Position> tracePath(IGameMap gameMap, Position from, Position goal) {
        assert from != goal : "from != goal; should be checked before call this method";
        if (from.equals(goal))
            return PathUtil.getInstance().EmptyPath;


        PathfinderNode startNode = null;
        PathfinderNode goalNode = null;
        NodeQueue<PathfinderNode> openQueue = null;
        NodesSet<PathfinderNode> closedSet = null;
        try {
            startNode = PathUtil.getInstance().NodesPool.claim(new Timeout(1, TimeUnit.SECONDS));
            startNode.setPosition(from);
            goalNode = PathUtil.getInstance().NodesPool.claim(new Timeout(1, TimeUnit.SECONDS));
            goalNode.setPosition(goal);

            var node = PathUtil.getInstance().NodesPool.claim(new Timeout(1, TimeUnit.SECONDS));
            openQueue = PathUtil.getInstance().QueuePool.claim(new Timeout(1, TimeUnit.SECONDS));
            closedSet = PathUtil.getInstance().Setpool.claim(new Timeout(1, TimeUnit.SECONDS));

            node.setPosition(from);
            openQueue.add(startNode);
            var size = 0;
            while (!openQueue.isEmpty()) {
                if (closedSet.contains(goalNode))
                    return PathUtil.getInstance().EmptyPath;

                var current = openQueue.poll();
                assert current != null;
                assert current.getPosition() != null;
                assert !closedSet.contains(current);

                closedSet.add(current);
                if (current.getPosition().equals(goal)) {
                    var path = new ArrayList<Position>(size);
                    var tile = current;
                    while (tile != null) {
                        if (tile.getPosition().equals(startNode.getPosition())) break;

                        path.add(tile.getPosition());
                        tile = tile.getParentNode();
                    }

                    return path.reversed();
                }

                var neighbors = getNeighbors(current.getPosition());
                for (var neighbor : neighbors) {
                    if (closedSet.contains(neighbor)) {
                        neighbor.release();
                        continue;
                    }
//                    if (!CanWalk(current.Position, neighbor, goal))
//                    {
//                        closedSet.Add(neighbor.Position);
//                        continue;
//                    }

                    var walkHeight = 0d;//GetWalkHeightOfMovement(current.Position, neighbor);
                    neighbor.getPosition().setZ(walkHeight);
                    neighbor.setParentNode(current);
                    if (!openQueue.contains(neighbor)) {
                        openQueue.add(neighbor);
                        neighbor.setGCosts(calculateGCost(current.getPosition(), neighbor.getPosition(), true));
                        neighbor.setHCosts(calculateHCost(neighbor.getPosition(), goal));
                        size++;
                    } else if (BasicCost + neighbor.getHCosts() < neighbor.getFCosts()) {
                        neighbor.setGCosts(BasicCost);
                        size++;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("exception at pathfinder: {}", e.getMessage(), e);
        } finally {
            if (openQueue != null) openQueue.fullRelease();
            if (closedSet != null) closedSet.fullRelease();
            if (startNode != null && !startNode.isFree()) startNode.release();
            if (goalNode != null && !goalNode.isFree()) goalNode.release();
        }


        return PathUtil.getInstance().EmptyPath;
    }

    private NodesSet<PathfinderNode> getNeighbors(Position position) throws InterruptedException {
        assert position != null;

        var set = PathUtil.getInstance().Setpool.claim(new Timeout(1, TimeUnit.SECONDS));
        assert set != null;
        for (var direction : diagonalDirections.values()) {
            var neighborPosition = position.add(direction);
            var node = PathUtil.getInstance().NodesPool.claim(new Timeout(1, TimeUnit.SECONDS));
            assert node != null;
            node.setPosition(neighborPosition);
            set.add(node);
        }
        return set;
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }
}